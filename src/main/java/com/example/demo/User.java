package com.example.demo;

import java.util.UUID;

public class User {
    private String address;
    private String nonce = UUID.randomUUID().toString();

    public User(String address) {
        this.address = address;
        this.nonce = UUID.randomUUID().toString();
    }

    public User(String address, String nonce) {
        this.address = address;
        this.nonce = nonce;
    }

    public String getAddress() {
        return address;
    }

    public String getNonce() {
        return nonce;
    }
}
