package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.AdminUserRepository;
import com.example.demo.models.AdminUser;

import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminUserRepository adminRepo;

    // Método para registrar o usuário
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        System.out.println("Received email: " + user.getEmail());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "Email already exists"));
        }

        userRepository.save(user);
            return ResponseEntity.ok("Usuario registrado");
    }

    // Método para fazer o login do usuário
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel user) {
        System.out.println("Received email for login attempt: " + user.getEmail());

        UserModel existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (existingUser == null) {
            System.out.println("User not found in database.");
            return ResponseEntity.badRequest().body(Map.of("Failure", "Invalid credentials"));
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            System.out.println("Password mismatch for user: " + existingUser.getEmail());
            return ResponseEntity.badRequest().body(Map.of("Failure", "Invalid credentials"));
        }

        // Remove the password from the response
        existingUser.setPassword(null);

        // Log the user data before returning
        System.out.println("User found: " + existingUser);

        Map<String, Object> response = new HashMap<>();
        response.put("user", existingUser); // CORRIGIR!!!!!!!!!!!!!!!!!!!!!!!!

        return ResponseEntity.ok(response);
    }

    // Método para atualizar os dados do usuário
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserModel updatedUser) {
        // Encontre o usuário pelo ID
        UserModel existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "User not found"));
        }

        // Atualizar os dados do usuário
        if (updatedUser.getNome() != null) {
            existingUser.setNome(updatedUser.getNome());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getGenero() != null) {
            existingUser.setGenero(updatedUser.getGenero());
        }
        if (updatedUser.getDataNascimento() != null) {
            existingUser.setDataNascimento(updatedUser.getDataNascimento());
        }
        if (updatedUser.getEnderecoFaturamento() != null) {
            existingUser.setEnderecoFaturamento(updatedUser.getEnderecoFaturamento());
        }

        // Salvar o usuário atualizado no banco de dados
        userRepository.save(existingUser);

        // Remover a senha antes de enviar a resposta
        existingUser.setPassword(null);

        // Retornar a resposta
        return ResponseEntity.ok(Map.of("Success", "User updated successfully", "user", existingUser));
    }
    
    @PostMapping("/backofficelogin")
    public ResponseEntity<?> login(@RequestBody AdminUser credentials) {
        Optional<AdminUser> userOpt = adminRepo.findByName(credentials.getName());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado.");
        }

        AdminUser user = userOpt.get();

        if (!user.getPassword().equals(credentials.getPassword())) {
            return ResponseEntity.status(401).body("Senha incorreta.");
        }

        return ResponseEntity.ok("Login bem-sucedido para o usuário: " + user.getName());
    }
    
    @PostMapping("/backofficecadastro")
    public ResponseEntity<?> register(@RequestBody AdminUser user) {
        Optional<AdminUser> existing = adminRepo.findByName(user.getName());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe.");
        }

        adminRepo.save(user);
        return ResponseEntity.ok("Cadastro realizado com sucesso.");
    }
}
