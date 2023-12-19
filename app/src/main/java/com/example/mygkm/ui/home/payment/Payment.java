package com.example.mygkm.ui.home.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mygkm.R;
import com.example.mygkm.databinding.ActivityPaymentBinding;
import com.example.mygkm.models.Order;
import com.example.mygkm.models.Product;
import com.example.mygkm.models.User;
import com.example.mygkm.repository.AuthRepository;
import com.example.mygkm.repository.FirestoreRepository;
import com.example.mygkm.ui.home.payment.success.Success;
import com.example.mygkm.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Payment extends AppCompatActivity {
    ActivityPaymentBinding binding;
    Product product;
    User user;
    int quantity;
    String metodePemesanan;
    String address;
    String paymentMethod;
    BottomSheetDialog metodePemesananDialog;
    BottomSheetDialog metodePembayaranDialog;
    FirestoreRepository firestoreRepository;
    AuthRepository authRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        firestoreRepository = new FirestoreRepository();
        authRepository = new AuthRepository();
        try {
            quantity = intent.getIntExtra("quantity", 1);
            product = (Product) intent.getSerializableExtra("product");
        }catch (Exception e){
            finish();
        }
        if(product==null){
            finish();
        }
        firestoreRepository.getUser(authRepository.getCurrentUser().getUid(),documentSnapshot -> {
            user = documentSnapshot.toObject(User.class);
        }, e -> {});
        metodePemesananDialog = new BottomSheetDialog(this);
        metodePembayaranDialog = new BottomSheetDialog(this);

        createTipePemesananDialog();
        createPaymentDialog();

        binding.ubahTipePesanan.setOnClickListener(v -> {
            metodePemesananDialog.show();
        });

        binding.ubahPayment.setOnClickListener(v -> {
            metodePembayaranDialog.show();
        });

        metodePemesananDialog.setOnDismissListener(dialog -> {
            binding.tipePemesanan.setText(metodePemesanan);
            binding.location.setText(address);
        });


        paymentMethod = "Tunai";
        metodePemesanan = "Ambil Sendiri";
        address = "";

        binding.tipePemesanan.setText(metodePemesanan);
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.paymentMethod.setText(paymentMethod);
        Picasso.get().load(product.getImageUrl()).into(binding.productImage);
        binding.productName.setText(product.getName());
        binding.productPrice.setText(Utils.formatRupiah(product.getPrice()));
        binding.quantity.setText(quantity+"");
        binding.grandTotal.setText(Utils.formatRupiah(quantity*product.getPrice()));
        binding.backButton.setOnClickListener(v -> {
            finish();
        });
        binding.increaseButton.setOnClickListener(v -> {
            quantity++;
            changeQuantity();
        });

        binding.decreaseButton.setOnClickListener(v -> {
            if(quantity>1)quantity--;
            changeQuantity();
        });

        binding.pesanButton.setOnClickListener(v -> {
            if(user==null)return;
            if(metodePemesanan.equals("Diantar") && address.isEmpty()){
                Toast.makeText(this, "Please fill the address", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = new Order(user.getFirstName()+" "+user.getLastName(), quantity, Utils.getCurrentFormattedDate(),Utils.getCurrentFormattedTime(), true,product.getPrice()*quantity, product.getId(), paymentMethod, metodePemesanan);
            order.setUid(authRepository.getCurrentUser().getUid());
            order.setAddress(address);

            firestoreRepository.addPesanan(order, task -> {
                if(task.isSuccessful()){
                    Intent i = new Intent(this, Success.class);
                    startActivity(i);
                    finish();
                }
            }, e -> {
                Toast.makeText(this, "Failed because "+e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

    }

    private void createTipePemesananDialog() {
        View view = getLayoutInflater().inflate(R.layout.tipe_pemesanan_dialog, null, false);
        MaterialCardView diantarCardView = view.findViewById(R.id.diantarChoice);
        MaterialCardView ambilSendiriCardView = view.findViewById(R.id.ambilSendiriChoice);
        TextInputEditText addressEditText = view.findViewById(R.id.lokasiEditText);
        AppCompatButton button = view.findViewById(R.id.simpanButton);

        diantarCardView.setOnClickListener(v -> {
            diantarCardView.setStrokeColor(getColor(R.color.primary));
            ambilSendiriCardView.setStrokeColor(getColor(R.color.grey));
            metodePemesanan = "Diantar";
        });

        ambilSendiriCardView.setOnClickListener(v -> {
            diantarCardView.setStrokeColor(getColor(R.color.grey));
            ambilSendiriCardView.setStrokeColor(getColor(R.color.primary));
            metodePemesanan = "Ambil Sendiri";
        });

        button.setOnClickListener(v -> {
            if(metodePemesanan.equals("Diantar")){
                String text = addressEditText.getText().toString();
                if(text.isEmpty()){
                    Toast.makeText(this, "Please fill the address", Toast.LENGTH_SHORT).show();
                }else{
                    address = text;
                    metodePemesananDialog.dismiss();
                }
            }else{
                metodePemesananDialog.dismiss();
            }
        });

        metodePemesananDialog.setContentView(view);
    }

    private void createPaymentDialog() {
        View view = getLayoutInflater().inflate(R.layout.metode_pembayaran_dialog, null, false);
        RadioGroup pembayaranGroup = view.findViewById(R.id.pembayaranGroup);
        AppCompatButton button = view.findViewById(R.id.simpanButton);
        pembayaranGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String tempPayment ="";
            int imgResource = -1;
            if (checkedId == R.id.shopee) {
                tempPayment = "Shopeepay";
                imgResource = R.drawable.spaymini;
            } else if (checkedId == R.id.gopay) {
                tempPayment = "Gopay";
                imgResource = R.drawable.gopaymini;
            } else if (checkedId == R.id.tunai) {
                tempPayment = "Tunai";
                imgResource = R.drawable.ic_cash;
            } else if (checkedId == R.id.dana) {
                tempPayment = "Dana";
                imgResource = R.drawable.danamini;
            }
            paymentMethod = tempPayment;

            if(!tempPayment.isEmpty() && imgResource!=-1){
                binding.paymentMethod.setText(tempPayment);
                binding.paymentImage.setImageResource(imgResource);
            }
        });

        button.setOnClickListener(v -> {
            metodePembayaranDialog.dismiss();
        });

        metodePembayaranDialog.setContentView(view);
    }

    void changeQuantity(){
        binding.quantity.setText(quantity+"");
        binding.grandTotal.setText(Utils.formatRupiah(quantity*product.getPrice()));
    }
}