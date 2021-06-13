package tr.edu.yildiz.payci.soner.wardrobe.dal;


import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {
    public static FirebaseDatabase getDatabaseEngine () {
        return FirebaseDatabase.getInstance();
    }

}
