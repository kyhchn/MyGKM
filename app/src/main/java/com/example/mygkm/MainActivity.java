package com.example.mygkm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mygkm.databinding.ActivityMainBinding;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.ui.home.Home;
import com.example.mygkm.ui.login.Login;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.units.qual.A;

public class MainActivity extends AppCompatActivity {
    AuthRepository authRepository;
    ActivityMainBinding binding;
    boolean isOld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authRepository = new AuthRepository();
        Intent intent = getIntent();
        isOld = intent.getBooleanExtra("isOld", false);
        if(isOld){
            binding.layout.setBackgroundResource(R.color.white);
            transformPath();
        }else{
            new Handler().postDelayed(() -> {
                transformPath();
            }, 2000);
        }
    }

    private void transformPath(){
        FirebaseUser currentUser = authRepository.getCurrentUser();
        if (currentUser == null) {
            // User is not logged in, redirect to Login
            Intent i = new Intent(MainActivity.this, Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            // User is logged in, redirect to Home
            Intent i = new Intent(MainActivity.this, Home.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }
}