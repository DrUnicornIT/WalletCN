package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public void register(User user) {
        users.put(user.getAddress(), user);
    }

    public User findByAddress(String address) {
        return users.get(address);
    }
}
