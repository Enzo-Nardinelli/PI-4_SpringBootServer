package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        System.out.println("Received email: " + user.getEmail());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "Email already exists"));
        }

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("Success", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel user) {
        System.out.println("Received email: " + user.getEmail());

        UserModel existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "Invalid credentials"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userEmail", existingUser.getEmail());
        response.put("userCarrinho", existingUser.getCarrinho());
        response.put("userJogos", existingUser.getJogos());

        return ResponseEntity.ok(response);
    }

}

