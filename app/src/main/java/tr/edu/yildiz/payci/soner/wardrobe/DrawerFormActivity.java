package tr.edu.yildiz.payci.soner.wardrobe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.adapters.ClothingAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Drawer;

public class DrawerFormActivity extends AppCompatActivity {
    private ClothingAdapter mAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    View saveDrawerButton;
    TextView drawerNameTxtElement;
    ArrayList<Clothing> clothes;
    Drawer drawer;
    String drawerGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_form);
        defineElements();

        RecyclerView recyclerView = this.findViewById(R.id.recycler_view_clothing_list);
        mAdapter = new ClothingAdapter(getApplicationContext(), clothes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        recyclerView.setFocusable(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);


        defineListeners();

    }

    public void defineElements(){
        saveDrawerButton =  this.findViewById(R.id.save_drawer_btn);
        drawerNameTxtElement = this.findViewById(R.id.drawer_name_text);
        clothes = new ArrayList<>();

        if (getIntent().hasExtra("drawerGuid")) {
            Bundle extras = getIntent().getExtras();
            drawerGuid = extras.getString("drawerGuid");
        }

        if (drawerGuid == null) {
            database.getReference().child("clothes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mAdapter.getItemCount() > 0) {
                        clothes = new ArrayList<>();
                        mAdapter.empty();
                    }

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
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("error",error.getMessage());
                }
            });

        } else {
            database.getReference().child("drawers").child(drawerGuid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mAdapter.getItemCount() > 0) {
                        clothes = new ArrayList<>();
                        mAdapter.empty();
                    }
                    if (dataSnapshot.getValue() != null) {
                        drawer = dataSnapshot.getValue(Drawer.class);
                        drawerNameTxtElement.setText(drawer.getName());
                        for (Clothing clothing : drawer.getClothes()) {
                            mAdapter.addItem(clothing);
                        }

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


    }

    public void defineListeners(){
        saveDrawerButton.setOnClickListener(v -> {


            String drawerName = drawerNameTxtElement.getText().toString();

            if (drawer == null) {
                drawer = new Drawer(drawerName);

                DatabaseReference ref = database.getReference().child("drawers").push();
                drawer.setGuid(ref.getKey());
                drawer.setClothes(mAdapter.getSelectedItems());
                ref.setValue(drawer, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Drawer", databaseError.toException());
                    }
                });
            } else {
                DatabaseReference ref = database.getReference().child("drawers").child(drawer.getGuid());
                drawer.setName(drawerName);
                drawer.setClothes(mAdapter.getSelectedItems());
                ref.setValue(drawer, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Drawer", databaseError.toException());
                    }
                });
            }

            finish();
            super.onBackPressed();
        });


    }




}