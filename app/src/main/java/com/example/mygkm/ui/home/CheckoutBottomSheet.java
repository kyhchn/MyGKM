package com.example.mygkm.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mygkm.R;
import com.example.mygkm.databinding.FragmentCheckoutBottomSheetBinding;
import com.example.mygkm.models.Product;
import com.example.mygkm.models.User;
import com.example.mygkm.ui.home.payment.Payment;
import com.example.mygkm.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class CheckoutBottomSheet extends BottomSheetDialogFragment {
    Product product;
    FragmentCheckoutBottomSheetBinding binding;
    int quantity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product =(Product) getArguments().getSerializable("product");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBottomSheetBinding.inflate( inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quantity = 1;
        Picasso.get().load(product.getImageUrl()).into(binding.productImage);
        binding.productPrice.setText(Utils.formatRupiah(product.getPrice()));
        binding.productName.setText(product.getName());
        binding.productDesc.setText(product.getDesc());
        binding.quantity.setText(quantity+"");

        binding.orderButton.setOnClickListener(v -> {
            Log.d("order", "start");
            Intent i = new Intent(requireActivity(), Payment.class);
            i.putExtra("product", product);
            i.putExtra("quantity", quantity);
            getContext().startActivity(i);
            Log.d("order", "finish");
            dismiss();
        });

        binding.increaseButton.setOnClickListener(v -> {
            quantity++;
            binding.quantity.setText(quantity+"");
            binding.productPrice.setText(Utils.formatRupiah(product.getPrice()*quantity));
        });

        binding.decreaseButton.setOnClickListener(v -> {
            Log.d("clicked", "onViewCreated: ");
            if(quantity>1)quantity--;
            binding.quantity.setText(quantity+"");
            binding.productPrice.setText(Utils.formatRupiah(product.getPrice()*quantity));
        });
    }
}