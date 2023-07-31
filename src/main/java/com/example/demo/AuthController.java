package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService users;
    private final AuthenticationManager manager;

    public AuthController(UserService users, AuthenticationManager manager) {
        this.users = users;
        this.manager = manager;
    }

    @GetMapping("/challenge/{address}")
    public String challenge(@PathVariable String address) {
        User user = users.findByAddress(address);
        if (user != null) {
            return user.getNonce();
        } else {
            throw new UnknownAddressException(address);
        }
    }

    @PostMapping("/auth")
    public Authentication auth(@RequestBody SignRequest request) {
        return manager.authenticate(new Web3Authentication(request.getAddress(), request.getSignature()));
    }
}

class SignRequest {
    private String signature;
    private String address;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnknownAddressException extends RuntimeException {
    public UnknownAddressException(String address) {
        super("Wallet address " + address + " is not known");
    }
}
