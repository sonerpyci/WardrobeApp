package tr.edu.yildiz.payci.soner.wardrobe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;

import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Photo;
import tr.edu.yildiz.payci.soner.wardrobe.helpers.IOStream;
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
    byte[] selectedImageByteArray;


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
        selectedImageByteArray = new byte[] {};

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

            byte[] base64PhotoArray = Base64.encode(selectedImageByteArray, selectedImageByteArray.length);
            String base64PhotoContent = new String(base64PhotoArray);
            Photo clothingPhoto = new Photo(base64PhotoContent);

            Clothing clothing = new Clothing(clothingName, clothingType, clothingColor, clothingPrice, clothingPhoto);

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

                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                selectedImageByteArray = IOStream.readAllBytes(inputStream);

                Bitmap bmp = BitmapFactory.decodeByteArray(selectedImageByteArray, 0, selectedImageByteArray.length);
                ImageView image = (ImageView) findViewById(R.id.clothing_image_holder);
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));

            } catch (IOException e) {
                Toast.makeText(ClothingFormActivity.this, "Sistem Belirtilen Dosyayı Bulamıyor.", Toast.LENGTH_SHORT).show();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...


        }
    }



}