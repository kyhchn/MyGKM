package com.example.mygkm.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mygkm.MainActivity;
import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityLoginBinding;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.ui.register.Register;
import com.example.mygkm.utils.Utils;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        authRepository = new AuthRepository();
        setContentView(binding.getRoot());

        binding.registerButton.setOnClickListener(v -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();

            if(Utils.isNotEmpty(email) && Utils.isNotEmpty(password)){
                if(Utils.isValidEmail(email)){
                    authRepository.loginUser(email, password, new AuthRepository.AuthCallback() {
                        @Override
                        public void onSuccess(FirebaseUser user) {
                            Intent i = new Intent(Login.this, MainActivity.class);
                            i.putExtra("isOld", true);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Make sure all field is filled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}