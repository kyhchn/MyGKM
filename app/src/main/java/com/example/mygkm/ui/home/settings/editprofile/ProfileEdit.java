package com.example.mygkm.ui.home.settings.editprofile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityProfileEditBinding;
import com.example.mygkm.models.User;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.repository.FirebaseStorageRepository;
import com.example.mygkm.repository.FirestoreRepository;
import com.example.mygkm.ui.home.Home;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileEdit extends AppCompatActivity {
    ActivityProfileEditBinding binding;
    User user;
    Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseStorageRepository firebaseStorageRepository;
    FirestoreRepository firestoreRepository;
    AuthRepository authRepository;
    String[] prodi = {"Sistem Informasi", "Pendidikan Teknologi Informasi", "Teknologi Informasi", "Teknik Informatika", "Teknik Komputer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseStorageRepository = new FirebaseStorageRepository();
        firestoreRepository = new FirestoreRepository();
        authRepository = new AuthRepository();
        Intent intent = getIntent();
        try {
            user = (User) intent.getSerializableExtra("user");
        }catch (Exception e){
            finish();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, prodi);
        binding.prodiDropdown.setAdapter(adapter);

        binding.firstName.setText(user.getFirstName());
        binding.lastName.setText(user.getLastName());
        binding.nimEditText.setText(user.getNim());
        binding.prodiDropdown.setText(user.getProdi(), false);
        if(!user.getImageUrl().isEmpty()){
            Picasso.get().load(user.getImageUrl()).into(binding.userProfileImage);
        }

        binding.userProfileImage.setOnClickListener(v -> {
            Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageIntent.setType("image/*");

            // Start the image picker activity
            startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST);
        });

        binding.simpanButton.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();
            String firstName = binding.firstName.getText().toString();
            String lastName = binding.lastName.getText().toString();
            String nim = binding.nimEditText.getText().toString();
            String prodi = binding.prodiDropdown.getText().toString();

            if(!firstName.equals(user.getFirstName())){
                updates.put("firstName", firstName);
            }
            if(!lastName.equals(user.getLastName())){
                updates.put("lastName", lastName);
            }
            if(!nim.equals(user.getNim())){
                updates.put("nim", nim);
            }
            if(!prodi.equals(user.getProdi())){
                updates.put("prodi", prodi);
            }
            if(imageUri!=null){
                firebaseStorageRepository.uploadImage(imageUri,uri -> {
                    updates.put("imageUrl", uri.toString());
                    String uid = authRepository.getCurrentUser().getUid();
                    firestoreRepository.updateUser(uid, updates, unused -> {
                        Intent i = new Intent(this, Home.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }, e -> {});
                },e -> {

                });
            }else{
                String uid = authRepository.getCurrentUser().getUid();
                firestoreRepository.updateUser(uid, updates, unused -> {
                    Intent i = new Intent(this, Home.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }, e -> {});
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageUri = selectedImageUri;
            binding.userProfileImage.setImageURI(imageUri);
        }
    }
}