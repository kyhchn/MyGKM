package com.example.mygkm.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mygkm.models.Order;
import com.example.mygkm.models.Product;
import com.example.mygkm.models.User;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.repository.FirestoreRepository;
import com.example.mygkm.ui.home.fragments.Riwayat;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeSharedViewModel extends ViewModel {
    private AuthRepository authRepository = new AuthRepository();
    private List<Order> tempOrders = new ArrayList<>();
    private List<DocumentSnapshot> documentSnapshots = new ArrayList<>();
    private FirestoreRepository firestoreRepository = new FirestoreRepository();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<List<Product>> productList = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Order>> orderList = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<List<Order>> getRiwayatList() {
        return orderList;
    }

    private MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>(authRepository.getCurrentUser());

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<List<Product>> getProductList() {
        return productList;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        return firebaseUser;
    }

    public void logout(){
        authRepository.logout();
    }

    public void fetchUser(){
        firestoreRepository.getUser(getFirebaseUser().getValue().getUid(), documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            this.user.postValue(user);
        }, e -> {

        });
    }

    public void fetchOrder(){
        firestoreRepository.getPesanan(getFirebaseUser().getValue().getUid(), queryDocumentSnapshots -> {
            documentSnapshots = queryDocumentSnapshots.getDocuments();
            tempOrders.clear();
            fetchProductsForOrders(documentSnapshots, tempOrders, 0);
        }, e -> {
            Log.d("fetch riwayat", e.getMessage());
        });
    }

    public void fetchProduct(){
        firestoreRepository.getProduct(queryDocumentSnapshots -> {
            Log.d("fetch riwayat", "start");
            List<Product> productList = new ArrayList<>();
            queryDocumentSnapshots.getDocuments().forEach(documentSnapshot -> {
                productList.add(documentSnapshot.toObject(Product.class));
            });
            this.productList.postValue(productList);
        }, e ->{} );
    }

    private void fetchProductsForOrders(List<DocumentSnapshot> documents, List<Order> result, int index) {
        if (index < documents.size()) {
            Log.d("fetch riwayat", "recursive called on index" + index+" docs size is "+documents.size());
            DocumentSnapshot document = documents.get(index);
            Order order = document.toObject(Order.class);
            firestoreRepository.getProductById(order.getProductId(), queryDocumentSnapshots -> {

                for (DocumentSnapshot productSnapshot : queryDocumentSnapshots) {
                    Product product = productSnapshot.toObject(Product.class);
                    order.setProduct(product);
                    result.add(order);
                    Log.d("fetch riwayat", "succes got product in index " + index);
                    Log.d("fetch riwayat", "result length "+result.size()+" docs length "+documents.size());
                    break;
                }
                if (result.size() == documents.size()) {
                    Log.d("fetch riwayat", "publish the orderlist " + index);
                    Log.d("fetch riwayat", order.getBuyerName());
                    orderList.postValue(result);
                    return;
                }
                fetchProductsForOrders(documents, result, index + 1);
            }, e -> {
                Log.d("fetch riwayat", e.getMessage());
            });
        } else {
        }
    }
}
