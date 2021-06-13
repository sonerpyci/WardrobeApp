package tr.edu.yildiz.payci.soner.wardrobe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.adapters.ClothingAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.dal.StorageHelper;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Combine;
import tr.edu.yildiz.payci.soner.wardrobe.helpers.SerializableManager;

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
    String combineGuid;
    View saveCombineBtn;
    TextView combineNameTxtElement;
    ArrayList<Clothing> clothes;
    ArrayAdapter<String> spinnerAdapter;
    View shareCombineButton;
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
        shareCombineButton =  this.findViewById(R.id.share_combine_btn_inner);
        combineNameTxtElement = this.findViewById(R.id.combine_name_text);
        clothingFirstSpinner = this.findViewById(R.id.clothing_first_spinner);
        clothingSecondSpinner = this.findViewById(R.id.clothing_second_spinner);
        clothingThirdSpinner = this.findViewById(R.id.clothing_third_spinner);
        clothingForthSpinner = this.findViewById(R.id.clothing_forth_spinner);
        clothingFifthSpinner = this.findViewById(R.id.clothing_fifth_spinner);

        if (getIntent().hasExtra("combineGuid")) {
            Bundle extras = getIntent().getExtras();
            combineGuid = extras.getString("combineGuid");
        }

        clothes = new ArrayList<>();

        if (combineGuid == null) {
            database.getReference().child("clothes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Clothing clothing = postSnapshot.getValue(Clothing.class);
                        mAdapter.addItem(clothing);
                    }
                    for (int i=0; i<clothes.size(); i++)
                    {
                        Log.d("FirebaseRead", "Combine with Name: " + clothes.get(i).getName());
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
        } else {

            FirebaseDatabase.getInstance()
                    .getReference("clothes").get().continueWith(task -> {
                if (task.isCanceled()) {
                    // Handle the error...
                }
                else if (task.isComplete()) {
                    DataSnapshot clothesSnapshot = task.getResult();
                    for (DataSnapshot postSnapshot : clothesSnapshot.getChildren()) {
                        Clothing clothing = postSnapshot.getValue(Clothing.class);
                        mAdapter.addItem(clothing);
                    }
                    for (int i=0; i<clothes.size(); i++)
                    {
                        Log.d("FirebaseRead", "Combine with Name: " + clothes.get(i).getName());
                    }
                    clothes = mAdapter.getItems();
                    mAdapter.notifyDataSetChanged();
                    spinnerAdapter = new ArrayAdapter<String>(CombineFormActivity.this, android.R.layout.simple_spinner_dropdown_item, mAdapter.getItemGuids());

                    database.getReference().child("combines").child(combineGuid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                combine = dataSnapshot.getValue(Combine.class);
                                combineNameTxtElement.setText(combine.getName());


                                clothingFirstSpinner.setAdapter(spinnerAdapter);
                                firstClothing = combine.getFirstClothing();
                                clothingFirstSpinner.setSelection(spinnerAdapter.getPosition(firstClothing.getGuid()));
                                if (firstClothing.getPhoto() != null) {
                                    StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(firstClothing.getPhoto().getUid());
                                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                            String imageURL = uri.toString();
                                            Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.first_image));
                                        }).addOnFailureListener(exception -> {
                                    });
                                }
                                clothingFirstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        firstClothing = mAdapter.getItemByGuid(clothingFirstSpinner.getSelectedItem().toString());
                                        if (firstClothing.getPhoto() != null) {
                                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(firstClothing.getPhoto().getUid());
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                String imageURL = uri.toString();
                                                Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.first_image));
                                            }).addOnFailureListener(exception -> {
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {}
                                });



                                clothingSecondSpinner.setAdapter(spinnerAdapter);
                                secondClothing = combine.getSecondClothing();
                                clothingSecondSpinner.setSelection(spinnerAdapter.getPosition(secondClothing.getGuid()));
                                if (secondClothing.getPhoto() != null) {
                                    StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(secondClothing.getPhoto().getUid());
                                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageURL = uri.toString();
                                        Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.second_image));
                                    }).addOnFailureListener(exception -> {
                                    });
                                }
                                clothingSecondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        secondClothing = mAdapter.getItemByGuid(clothingSecondSpinner.getSelectedItem().toString());
                                        if (secondClothing.getPhoto() != null) {
                                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(secondClothing.getPhoto().getUid());
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                String imageURL = uri.toString();
                                                Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.second_image));
                                            }).addOnFailureListener(exception -> {
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {}
                                });


                                clothingThirdSpinner.setAdapter(spinnerAdapter);
                                thirdClothing = combine.getThirdClothing();
                                clothingThirdSpinner.setSelection(spinnerAdapter.getPosition(thirdClothing.getGuid()));
                                if (thirdClothing.getPhoto() != null) {
                                    StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(thirdClothing.getPhoto().getUid());
                                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageURL = uri.toString();
                                        Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.third_image));
                                    }).addOnFailureListener(exception -> {
                                    });
                                }
                                clothingThirdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        thirdClothing = mAdapter.getItemByGuid(clothingThirdSpinner.getSelectedItem().toString());
                                        if (thirdClothing.getPhoto() != null) {
                                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(thirdClothing.getPhoto().getUid());
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                String imageURL = uri.toString();
                                                Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.third_image));
                                            }).addOnFailureListener(exception -> {
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {}
                                });


                                clothingForthSpinner.setAdapter(spinnerAdapter);
                                fourthClothing = combine.getFourthClothing();
                                clothingForthSpinner.setSelection(spinnerAdapter.getPosition(fourthClothing.getGuid()));
                                if (fourthClothing.getPhoto() != null) {
                                    StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fourthClothing.getPhoto().getUid());
                                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageURL = uri.toString();
                                        Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fourth_image));
                                    }).addOnFailureListener(exception -> {
                                    });
                                }
                                clothingForthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        fourthClothing = mAdapter.getItemByGuid(clothingForthSpinner.getSelectedItem().toString());
                                        if (fourthClothing.getPhoto() != null) {
                                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fourthClothing.getPhoto().getUid());
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                String imageURL = uri.toString();
                                                Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fourth_image));
                                            }).addOnFailureListener(exception -> {
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {}
                                });


                                clothingFifthSpinner.setAdapter(spinnerAdapter);
                                fifthClothing = combine.getFifthClothing();
                                clothingFifthSpinner.setSelection(spinnerAdapter.getPosition(fifthClothing.getGuid()));
                                if (fifthClothing.getPhoto() != null) {
                                    StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fifthClothing.getPhoto().getUid());
                                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageURL = uri.toString();
                                        Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fifth_image));
                                    }).addOnFailureListener(exception -> {
                                    });
                                }
                                clothingFifthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        fifthClothing = mAdapter.getItemByGuid(clothingFifthSpinner.getSelectedItem().toString());
                                        if (fifthClothing.getPhoto() != null) {
                                            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(fifthClothing.getPhoto().getUid());
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                String imageURL = uri.toString();
                                                Glide.with(getApplicationContext()).load(imageURL).into((ImageView) CombineFormActivity.this.findViewById(R.id.fifth_image));
                                            }).addOnFailureListener(exception -> {
                                            });
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {}
                                });

                            }
                            for (int i=0; i<clothes.size(); i++)
                            {
                                Log.d("FirebaseRead", "Drawer with Name: " + clothes.get(i).getName());
                            }
                            clothes = mAdapter.getItems();
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.e("error",error.getMessage());
                        }
                    });

                }
                return task;
            });
        }
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


        shareCombineButton.setOnClickListener(v -> {
            if (combine == null) {
                Toast.makeText(getApplicationContext(), "Kombin Kaydedilmeden Paylaşılamaz.", Toast.LENGTH_SHORT).show();
                return;
            }
            shareCombine(v, combine);
        });

    }

    public void shareCombine(View view, Combine combine) {
        String filename = combine.getName().replace(" ", "")+".wrdrb";
        SerializableManager.saveSerializable(getApplicationContext(), combine, filename);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("text/plain");
        String folder = getApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(folder + "/" + filename);
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "share file with"));
    }
}