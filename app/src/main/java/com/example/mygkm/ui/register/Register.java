package com.example.mygkm.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.mygkm.utils.Utils.isNotEmpty;
import static com.example.mygkm.utils.Utils.isValidEmail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mygkm.MainActivity;
import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityRegisterBinding;
import com.example.mygkm.models.User;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.repository.FirestoreRepository;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    ActivityRegisterBinding binding;
    AuthRepository authRepository;
    FirestoreRepository firestoreRepository;
    String[] prodi = {"Sistem Informasi", "Pendidikan Teknologi Informasi", "Teknologi Informasi", "Teknik Informatika", "Teknik Komputer"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authRepository = new AuthRepository();
        firestoreRepository = new FirestoreRepository();
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, prodi);
        binding.prodiDropdown.setAdapter(adapter);

        binding.createAccount.setOnClickListener(v -> {
            String firstName = binding.firstName.getText().toString();
            String lastName = binding.lastName.getText().toString();
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();
            String nim = binding.nimEditText.getText().toString();
            String prodi = binding.prodiDropdown.getText().toString();

            if(isNotEmpty(firstName) && isNotEmpty(lastName) && isNotEmpty(email) && isNotEmpty(password) && isNotEmpty(nim) && isNotEmpty(prodi)){
                if(isValidEmail(email)){
                    authRepository.registerUser(email, password, new AuthRepository.AuthCallback() {
                        @Override
                        public void onSuccess(FirebaseUser user) {
                            User targetUser = new User(firstName, lastName, email, prodi, nim);
                            firestoreRepository.addUser(targetUser, user.getUid(), success -> {
                                Intent i = new Intent(Register.this, MainActivity.class);
                                i.putExtra("isOld", true);
                                startActivity(i);
                                finish();
                            }, e -> {
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(Register.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(this, "Make sure the email is valid", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Make sure all field is filled", Toast.LENGTH_SHORT).show();
            }
        });
    }
}