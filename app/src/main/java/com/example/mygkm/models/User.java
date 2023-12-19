package com.example.mygkm.models;

import java.io.Serializable;

public class User implements Serializable {
    String firstName;
    String lastName;
    String imageUrl;
    String email;
    String prodi;
    String nim;

    User(){}
    public User(String firstName, String lastName, String email, String prodi, String nim){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.prodi = prodi;
        this.nim = nim;
        imageUrl = "";
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNim() {
        return nim;
    }

    public String getProdi() {
        return prodi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
