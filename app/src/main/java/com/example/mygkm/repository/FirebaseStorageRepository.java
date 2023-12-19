package com.example.mygkm.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageRepository {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public void uploadImage(Uri fileUri, OnSuccessListener<Uri> onSuccessListener,OnFailureListener onFailureListener){
        StorageReference imageRef = storageRef.child("images/"+System.currentTimeMillis());
        UploadTask uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnFailureListener(onFailureListener).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(onSuccessListener);
            }
        });
    }
}
