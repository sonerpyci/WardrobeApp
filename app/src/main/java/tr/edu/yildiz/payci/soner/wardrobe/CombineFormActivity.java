package tr.edu.yildiz.payci.soner.wardrobe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.adapters.ClothingAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.dal.StorageHelper;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Combine;

public class CombineFormActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Spinner clothingFirstSpinner;
    Spinner clothingSecondSpinner;
    Spinner clothingThirdSpinner;
    Spinner clothingForthSpinner;
    Spinner clothingFifthSpinner;
    Clothing firstClothing;
    Clothing secondClothing;
    Clothing thirdClothing;
    Clothing fourthClothing;
    Clothing fifthClothing;
    private ClothingAdapter mAdapter;
    Combine combine;
    View saveCombineBtn;
    TextView combineNameTxtElement;
    ArrayList<Clothing> clothes;
    ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine_form);



        defineVariables();
        mAdapter = new ClothingAdapter(CombineFormActivity.this, clothes);



        defineListeners();

    }

    public void defineVariables(){
        saveCombineBtn =  this.findViewById(R.id.save_combine_btn);
        combineNameTxtElement = this.findViewById(R.id.combine_name_text);
        clothingFirstSpinner = this.findViewById(R.id.clothing_first_spinner);
        clothingSecondSpinner = this.findViewById(R.id.clothing_second_spinner);
        clothingThirdSpinner = this.findViewById(R.id.clothing_third_spinner);
        clothingForthSpinner = this.findViewById(R.id.clothing_forth_spinner);
        clothingFifthSpinner = this.findViewById(R.id.clothing_fifth_spinner);


        clothes = new ArrayList<>();
        database.getReference().child("clothes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Clothing clothing = postSnapshot.getValue(Clothing.class);
                    mAdapter.addItem(clothing);
                }
                for (int i=0; i<clothes.size(); i++)
                {
                    Log.d("FirebaseRead", "Drawer with Name: " + clothes.get(i).getName());
                }
                clothes = mAdapter.getItems();

                mAdapter.notifyDataSetChanged();

                spinnerAdapter = new ArrayAdapter<String>(CombineFormActivity.this, android.R.layout.simple_spinner_dropdown_item, mAdapter.getItemGuids());

                clothingFirstSpinner.setAdapter(spinnerAdapter);
                clothingFirstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        firstClothing = mAdapter.getItemByGuid(clothingFirstSpinner.getSelectedItem().toString());
                        if (firstClothing.getPhoto() != null) {
                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(firstClothing.getPhoto().getUid());
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageURL = uri.toString();
                                Glide.with(CombineFormActivity.this).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.first_image));
                            }).addOnFailureListener(exception -> {
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });
                clothingSecondSpinner.setAdapter(spinnerAdapter);
                clothingSecondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        secondClothing = mAdapter.getItemByGuid(clothingSecondSpinner.getSelectedItem().toString());
                        if (secondClothing.getPhoto() != null) {
                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(secondClothing.getPhoto().getUid());
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageURL = uri.toString();
                                Glide.with(CombineFormActivity.this).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.second_image));
                            }).addOnFailureListener(exception -> {
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });
                clothingThirdSpinner.setAdapter(spinnerAdapter);
                clothingThirdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        thirdClothing = mAdapter.getItemByGuid(clothingThirdSpinner.getSelectedItem().toString());
                        if (thirdClothing.getPhoto() != null) {
                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(thirdClothing.getPhoto().getUid());
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageURL = uri.toString();
                                Glide.with(CombineFormActivity.this).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.third_image));
                            }).addOnFailureListener(exception -> {
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });
                clothingForthSpinner.setAdapter(spinnerAdapter);
                clothingForthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fourthClothing = mAdapter.getItemByGuid(clothingForthSpinner.getSelectedItem().toString());
                        if (fourthClothing.getPhoto() != null) {
                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fourthClothing.getPhoto().getUid());
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageURL = uri.toString();
                                Glide.with(CombineFormActivity.this).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fourth_image));
                            }).addOnFailureListener(exception -> {
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });
                clothingFifthSpinner.setAdapter(spinnerAdapter);
                clothingFifthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fifthClothing = mAdapter.getItemByGuid(clothingFifthSpinner.getSelectedItem().toString());
                        if (fifthClothing.getPhoto() != null) {
                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fifthClothing.getPhoto().getUid());
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageURL = uri.toString();
                                Glide.with(CombineFormActivity.this).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fifth_image));
                            }).addOnFailureListener(exception -> {
                            });
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {}
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });



    }

    public void defineListeners(){
        saveCombineBtn.setOnClickListener(v -> {
            String combineName = combineNameTxtElement.getText().toString();

            if (combine == null) {
                combine = new Combine(combineName, firstClothing, secondClothing, thirdClothing, fourthClothing, fifthClothing);

                DatabaseReference ref = database.getReference().child("combines").push();
                combine.setGuid(ref.getKey());
                ref.setValue(combine, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Combine", databaseError.toException());
                    }
                });
            } else {
                DatabaseReference ref = database.getReference().child("combines").child(combine.getGuid());
                combine.setName(combineName);
                combine.setFirstClothing(firstClothing);
                combine.setSecondClothing(secondClothing);
                combine.setThirdClothing(thirdClothing);
                combine.setFourthClothing(fourthClothing);
                combine.setFifthClothing(fifthClothing);
                ref.setValue(combine, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Combine", databaseError.toException());
                    }
                });
            }

            finish();
            super.onBackPressed();
        });

    }
}