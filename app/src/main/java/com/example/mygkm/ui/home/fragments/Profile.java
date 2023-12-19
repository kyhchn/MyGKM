package com.example.mygkm.ui.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygkm.R;
import com.example.mygkm.databinding.FragmentProfileBinding;
import com.example.mygkm.models.User;
import com.example.mygkm.ui.home.HomeSharedViewModel;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment {
    FragmentProfileBinding binding;
    HomeSharedViewModel vm;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(HomeSharedViewModel.class);
        user = vm.getUser().getValue();

        if(user!=null){
            bindUser();
        }

        vm.getUser().observe(getViewLifecycleOwner(), user1 -> {
            user= user1;
            bindUser();
        });
    }

    private void bindUser(){
        if(user.getImageUrl()!=null && !user.getImageUrl().isEmpty()){
            Picasso.get().load(user.getImageUrl()).into(binding.userProfileImage);
        }
        binding.userName.setText(user.getFirstName()+" "+user.getLastName());
        binding.userNim.setText(user.getNim());
        binding.greetingText.setText("Selamat Datang, "+user.getFirstName()+"!");
        binding.userEmail.setText(user.getEmail());
        binding.userProdi.setText(user.getProdi());
    }
}