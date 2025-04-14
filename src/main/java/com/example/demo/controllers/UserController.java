package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PutMapping("/{email}/carrinho/add")
    public ResponseEntity<UserModel> addToCarrinho(@PathVariable String email, @RequestBody String jogoId) {
        String emailWithoutQuotes = email.replace("\"", "");
        String jogoIdWithoutQuotes = jogoId.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.addToCarrinho(jogoIdWithoutQuotes);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{email}/carrinho/remove")
    public ResponseEntity<UserModel> removeFromCarrinho(@PathVariable String email, @RequestBody String jogoId) {
        String emailWithoutQuotes = email.replace("\"", "");
        String jogoIdWithoutQuotes = jogoId.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);
        System.out.println(user.getEmail());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.removeFromCarrinho(jogoIdWithoutQuotes);//pode dar erro aqui
        userRepository.save(user);
        System.out.println(user.getEmail());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/carrinho/finalizar")
    public ResponseEntity<UserModel> finalizarCompra(@PathVariable String id) {
        String idWithoutQuotes = id.replace("\"", "");
        UserModel user = userRepository.findById(idWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.finalizarCompra(); // ← Ensure this method clears carrinho properly
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/{email}/carrinho/retorno")
    public ResponseEntity<?> getCarrinho(@PathVariable String email) {
        String emailWithoutQuotes = email.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getUserCarrinho());
    }
}
