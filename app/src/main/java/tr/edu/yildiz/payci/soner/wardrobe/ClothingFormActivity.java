package tr.edu.yildiz.payci.soner.wardrobe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import tr.edu.yildiz.payci.soner.wardrobe.dal.StorageHelper;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Photo;
import tr.edu.yildiz.payci.soner.wardrobe.helpers.Utilizer;

public class ClothingFormActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final Integer PICK_PHOTO_FOR_CLOTHING = 1;
    View imageHolder;
    View saveClothingButton;
    TextView clothingNameTxtElement;
    TextView clothingPriceTxtElement;
    Spinner clothingTypeSpinner;
    Spinner clothingColorSpinner;
    Uri selectedImageFilePath;
    String selectedImageUid;
    Photo clothingPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_form);



        defineElements();

        defineListeners();

    }

    public void defineElements() {
        imageHolder = this.findViewById(R.id.clothing_image_holder);
        saveClothingButton = this.findViewById(R.id.save_clothing_btn);
        clothingTypeSpinner = this.findViewById(R.id.clothing_type_spinner);

        String[] items = new String[]{"Baş", "Üstlük İç", "Üstlük Dış", "Altlık", "Ayakkabı"};
        ArrayAdapter<String> clothingTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        clothingTypeSpinner.setAdapter(clothingTypeAdapter);

        clothingColorSpinner = this.findViewById(R.id.clothing_color_spinner);
        String[] colors = new String[]{"Mavi", "Yeşil", "Kırmızı", "Beyaz", "Gri", "Siyah", "Pembe", "Mor", "Lacivert", "Diğer"};
        ArrayAdapter<String> clothingColorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colors);
        clothingColorSpinner.setAdapter(clothingColorAdapter);

    }

    public void defineListeners() {
        imageHolder.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Complete action using"),
                    PICK_PHOTO_FOR_CLOTHING);
        });

        saveClothingButton.setOnClickListener(v -> {

            clothingNameTxtElement = this.findViewById(R.id.clothing_name_text);
            clothingPriceTxtElement = this.findViewById(R.id.clothing_price_text);
            clothingTypeSpinner = this.findViewById(R.id.clothing_type_spinner);
            clothingColorSpinner = this.findViewById(R.id.clothing_color_spinner);



            String clothingName = String.valueOf(clothingNameTxtElement.getText());
            String clothingType = clothingTypeSpinner.getSelectedItem().toString();
            String clothingColor = clothingColorSpinner.getSelectedItem().toString();
            double clothingPrice = Utilizer.ParseDouble(String.valueOf(clothingPriceTxtElement.getText()));


            if (selectedImageFilePath != null) {
                // upload image on firebase and get url
                selectedImageUid = UUID.randomUUID().toString();
                StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(selectedImageUid);


                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();


                ref.putFile(selectedImageFilePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(ClothingFormActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e1) {

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ClothingFormActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e2) {

                            }
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

                clothingPhoto = new Photo(selectedImageUid);
            }

            Clothing clothing;
            if (clothingPhoto != null) {
                clothing = new Clothing(clothingName, clothingType, clothingColor, clothingPrice, clothingPhoto);
            } else {
                clothing = new Clothing(clothingName, clothingType, clothingColor, clothingPrice);
            }

            DatabaseReference ref = database.getReference().child("clothes").push();
            clothing.setGuid(ref.getKey());
            ref.setValue(clothing, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    Log.e("ERROR_INSERT", "Failed to write Clothing", databaseError.toException());
                }
            });

            finish();
            super.onBackPressed();
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_CLOTHING && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ClothingFormActivity.this, "Fotoğraf Okunamadı.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {

                Uri filePath = data.getData();

                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView image = (ImageView) findViewById(R.id.clothing_image_holder);
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
                selectedImageFilePath = filePath;

            } catch (IOException e) {
                Toast.makeText(ClothingFormActivity.this, "Sistem Belirtilen Dosyayı Bulamıyor.", Toast.LENGTH_SHORT).show();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...


        }
    }



}