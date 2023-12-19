package com.example.mygkm.repository;

import com.example.mygkm.models.Order;
import com.example.mygkm.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirestoreRepository {
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    public void getProduct(OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener){
        db.collection("products").get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public void getProductById(String productId,OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener){
        db.collection("products").whereEqualTo("id", productId).get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public void addPesanan(Order order, OnCompleteListener<DocumentReference> onCompleteListener, OnFailureListener onFailureListener){
        db.collection("orders").add(order).addOnCompleteListener(onCompleteListener).addOnFailureListener(onFailureListener);
    }

    public void getPesanan(String uid, OnSuccessListener<QuerySnapshot> onSuccessListener, OnFailureListener onFailureListener){
        db.collection("orders").whereEqualTo("uid", uid).get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public void getUser(String uid, OnSuccessListener<DocumentSnapshot> onSuccessListener, OnFailureListener onFailureListener){
        db.collection("users").document(uid).get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public void addUser(User user, String uid, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){
        DocumentReference userRef =db.collection("users").document(uid);
        userRef.set(user).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public void deleteUser (String uid, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){
        DocumentReference userRef = db.collection("users").document(uid);
        userRef.
                delete().
                addOnSuccessListener(onSuccessListener).
                addOnFailureListener(onFailureListener);
    }
    public void updateUser(String userId, Map<String, Object> updates, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.update(updates)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
