package com.example.mygkm.ui.home.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mygkm.R;
import com.example.mygkm.adapter.ProductAdapter;
import com.example.mygkm.databinding.FragmentPesanBinding;
import com.example.mygkm.models.Product;
import com.example.mygkm.models.User;
import com.example.mygkm.ui.home.HomeSharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class Pesan extends Fragment {
    FragmentPesanBinding binding;
    HomeSharedViewModel vm;
    List<Product> productList;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPesanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(HomeSharedViewModel.class);
        binding.greetingText.setText("Selamat Datang ");
        productList = new ArrayList<>();
        vm.fetchProduct();
        vm.getUser().observe(getViewLifecycleOwner(), user1 -> {
            if(user1!=null){
                user = user1;
                binding.greetingText.setText("Selamat Datang "+ user.getFirstName());
            }

        });
        ProductAdapter adapter = new ProductAdapter(productList, product -> {
            showBottomSheet(product);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        binding.productRecycler.setAdapter(adapter);
        binding.productRecycler.setLayoutManager(layoutManager);

        vm.getProductList().observe(getViewLifecycleOwner(), products -> {
            productList.clear();
            productList.addAll(products);
            adapter.notifyDataSetChanged();
        });


    }
    private void showBottomSheet(Product product){
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_pesan_to_checkoutBottomSheet, bundle);
    }
}