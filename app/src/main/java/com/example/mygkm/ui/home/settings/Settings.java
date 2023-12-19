package com.example.mygkm.ui.home.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mygkm.MainActivity;
import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivitySettingsBinding;
import com.example.mygkm.models.User;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.repository.FirestoreRepository;
import com.example.mygkm.ui.home.settings.editprofile.ProfileEdit;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding binding;
    AuthRepository authRepository;
    User user;
    FirestoreRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authRepository = new AuthRepository();
        repository = new FirestoreRepository();
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        Intent i = getIntent();
        User intentUser =(User) i.getSerializableExtra("user");
        if(intentUser==null){
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            user = intentUser;
        }
        setContentView(binding.getRoot());
        binding.logout.setOnClickListener(v -> {
            authRepository.logout();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("isOld", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        binding.editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileEdit.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
        binding.deleteAccount.setOnClickListener(v -> {
            repository.deleteUser(authRepository.getCurrentUser().getUid(),unused -> {
                authRepository.deleteAccount(task -> {
                    authRepository.logout();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("isOld", true);
                    startActivity(intent);
                    finish();
                },e -> {});
            },e -> {});
        });
    }
}