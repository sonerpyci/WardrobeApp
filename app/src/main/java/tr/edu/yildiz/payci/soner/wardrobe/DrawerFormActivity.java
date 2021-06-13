package tr.edu.yildiz.payci.soner.wardrobe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tr.edu.yildiz.payci.soner.wardrobe.entities.Drawer;

public class DrawerFormActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    View saveDrawerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_form);

        defineElements();
        defineListeners();

    }

    public void defineElements(){
        saveDrawerButton =  this.findViewById(R.id.save_drawer_btn);
    }

    public void defineListeners(){
        saveDrawerButton.setOnClickListener(v -> {
            // TODO : Save drawer to the Firebase

            TextView drawerNameTxtElement = this.findViewById(R.id.drawer_name_text);
            String drawerName = drawerNameTxtElement.getText().toString();
            Drawer drawer = new Drawer(drawerName);

            DatabaseReference ref = database.getReference().child("drawers").push();
            drawer.setGuid(ref.getKey());
            ref.setValue(drawer, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    Log.e("ERROR_INSERT", "Failed to write Drawer", databaseError.toException());
                }
            });

            finish();
            super.onBackPressed();
        });


    }




}