package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWalletApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserService users) {
        return args -> users.register(new User("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"));
    }
}
