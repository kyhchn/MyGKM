package com.example.mygkm.models;

import java.io.Serializable;

public class Product implements Serializable {
    public enum Type{
        FOOD,DRINK
    }
    String merchant;
    String name;
    String imageUrl;
    int price;
    Type type;
    String desc;
    String id;

    Product(){}

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
