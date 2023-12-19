package com.example.mygkm.ui.home.riwayatdetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityRiwayatDetailBinding;
import com.example.mygkm.models.Order;
import com.example.mygkm.utils.Utils;
import com.squareup.picasso.Picasso;

public class RiwayatDetail extends AppCompatActivity {
    ActivityRiwayatDetailBinding binding;
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRiwayatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        try {
            order =  (Order) intent.getSerializableExtra("order");
        }catch (Exception e){
            finish();
        }

        Picasso.get().load(order.getProduct().getImageUrl()).into(binding.productImage);
        binding.productName.setText(order.getProduct().getName());
        binding.merchantName.setText(order.getProduct().getMerchant());
        binding.buyerName.setText(order.getBuyerName());
        binding.arrowBackButton.setOnClickListener(v -> {
            finish();
        });
        binding.pesanLagiButton.setOnClickListener(v -> {
            finish();
        });
        binding.quantity.setText(order.getQuantity()+" Porsi");
        binding.orderDate.setText(order.getDate());
        binding.orderTime.setText(order.getTime());
        binding.orderStatus.setText("sukses");
        binding.orderMethod.setText(order.getTipePemesanan());
        binding.orderPaymentMethod.setText(order.getPaymentMethod());
        binding.grandTotal.setText(Utils.formatRupiah(order.getPrice()));
        binding.backButton.setOnClickListener(v -> {
            finish();
        });
        binding.pesanLagiButton.setOnClickListener(v -> {

        });
    }
}