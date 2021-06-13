package tr.edu.yildiz.payci.soner.wardrobe.dal;

import com.google.firebase.storage.FirebaseStorage;

public class StorageHelper {

    public static FirebaseStorage getStorageEngine () {
        return FirebaseStorage.getInstance();
    }
}
