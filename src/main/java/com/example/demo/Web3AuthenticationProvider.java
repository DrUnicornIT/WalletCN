package com.example.demo;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import java.security.SignatureException;

import java.math.BigInteger;

@Component
public class Web3AuthenticationProvider implements AuthenticationProvider {

    private final UserService users;

    public Web3AuthenticationProvider(UserService users) {
        this.users = users;
    }

    private boolean valid(String signature, String address, String nonce) {
        String r = signature.substring(0, 66);
        String s = "0x" + signature.substring(66, 130);
        String v = "0x" + signature.substring(130, 132);

        Sign.SignatureData data = new Sign.SignatureData(
                Numeric.hexStringToByteArray(v),
                Numeric.hexStringToByteArray(r),
                Numeric.hexStringToByteArray(s)
        );

        try {
            BigInteger key = Sign.signedPrefixedMessageToKey(nonce.getBytes(), data);
            return matches(key, address);
        } catch (SignatureException e) {
            e.printStackTrace(); // Or handle the exception according to your requirement
            return false; // Or return any appropriate value indicating failure
        }
    }



    private boolean matches(BigInteger key, String address) {
        return ("0x" + Keys.getAddress(key).toLowerCase()).equals(address.toLowerCase());
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = users.findByAddress(authentication.getName());
        if (user != null) {
            String signature = authentication.getCredentials().toString();
            if (valid(signature, user.getAddress(), user.getNonce())) {
                return new Web3Authentication(user.getAddress(), signature);
            }
        }

        throw new BadCredentialsException(authentication.getName() + " is not allowed to log in.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Web3Authentication.class.equals(authentication);
    }
}
