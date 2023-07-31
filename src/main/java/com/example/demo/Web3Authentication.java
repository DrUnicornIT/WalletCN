package com.example.demo;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class Web3Authentication extends AbstractAuthenticationToken {

    private final String address;
    private final String signature;

    public Web3Authentication(String address, String signature) {
        super(null);
        this.address = address;
        this.signature = signature;
        setAuthenticated(false);
    }

    @Override
    public Object getPrincipal() {
        return address;
    }

    @Override
    public Object getCredentials() {
        return signature;
    }
}
