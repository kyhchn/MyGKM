package com.example.mygkm.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Order implements Serializable {
    String buyerName;
    String uid;
    int quantity;
    String date;
    String time;
    boolean status;
    int price;
    String productId;
    @Nullable
    Product product;
    String paymentMethod;
    String tipePemesanan;
    @Nullable
    String address;

    Order (){}
    public Order(String buyerName, int quantity, String date, String time, boolean status, int price, String productId, String paymentMethod, String tipePemesanan){
        this.buyerName = buyerName;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.status = status;
        this.price = price;
        this.productId = productId;
        this.paymentMethod = paymentMethod;
        this.tipePemesanan = tipePemesanan;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTipePemesanan() {
        return tipePemesanan;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setProduct(@Nullable Product product) {
        this.product = product;
    }
}
