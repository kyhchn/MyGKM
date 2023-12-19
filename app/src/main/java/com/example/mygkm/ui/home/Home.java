package com.example.mygkm.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityHomeBinding;
import com.example.mygkm.models.Product;
import com.example.mygkm.models.User;
import com.example.mygkm.ui.home.settings.Settings;
import com.example.mygkm.utils.Utils;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    HomeSharedViewModel vm;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = new ViewModelProvider(this).get(HomeSharedViewModel.class);
        vm.fetchUser();
        vm.fetchOrder();

        NavHostFragment navHostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.homeFragmentContainer);
        NavController navController =navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavbar, navController);
        binding.settings.setOnClickListener(v -> {
            Intent i = new Intent(this, Settings.class);
            i.putExtra("user", user);
            startActivity(i);
        });

        binding.currentDate.setText(Utils.getCurrentFormattedDate());

        vm.getUser().observe(this, user1 -> {
            user = user1;
        });
    }
}