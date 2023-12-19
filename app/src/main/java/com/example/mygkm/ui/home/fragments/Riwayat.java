package com.example.mygkm.ui.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygkm.R;
import com.example.mygkm.adapter.OrderAdapter;
import com.example.mygkm.databinding.FragmentPesanBinding;
import com.example.mygkm.databinding.FragmentRiwayatBinding;
import com.example.mygkm.models.Order;
import com.example.mygkm.models.Product;
import com.example.mygkm.ui.home.HomeSharedViewModel;
import com.example.mygkm.ui.home.riwayatdetail.RiwayatDetail;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Riwayat extends Fragment {
    FragmentRiwayatBinding binding;
    HomeSharedViewModel vm;
    List<Order> orderList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentRiwayatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(HomeSharedViewModel.class);
        orderList = new LinkedList<>();
        OrderAdapter adapter = new OrderAdapter(orderList, order -> {
            Intent i = new Intent(requireActivity(), RiwayatDetail.class);
            i.putExtra("order", order);
            startActivity(i);
        }, order -> {
            showBottomSheet(order.getProduct());
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.riwayatRecycler.setLayoutManager(layoutManager);
        binding.riwayatRecycler.setAdapter(adapter);
        binding.sort.setOnClickListener(v -> {
            Collections.reverse(orderList);
            adapter.notifyDataSetChanged();
        });

        vm.getRiwayatList().observe(getViewLifecycleOwner(), orders -> {
            Log.d("fetch riwayat", "data found");
            for (Order order : orders) {
                Log.d("fetch riwayat", order.getBuyerName());
            }
            orderList.clear();
            orderList.addAll(orders);
            adapter.notifyDataSetChanged();
        });
    }
    private void showBottomSheet(Product product){
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_riwayat_to_checkoutBottomSheet, bundle);
    }
}