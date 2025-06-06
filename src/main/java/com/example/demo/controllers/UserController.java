package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint para adicionar um item ao carrinho
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

    // Endpoint para remover um item do carrinho
    @PutMapping("/{email}/carrinho/remove")
    public ResponseEntity<UserModel> removeFromCarrinho(@PathVariable String email, @RequestBody String jogoId) {
        String emailWithoutQuotes = email.replace("\"", "");
        String jogoIdWithoutQuotes = jogoId.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);
        
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.removeFromCarrinho(jogoIdWithoutQuotes);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Endpoint para finalizar a compra
    @PutMapping("/{email}/carrinho/finalizar")
    public ResponseEntity<UserModel> finalizarCompra(@PathVariable String email) {
        String emailWithoutQuotes = email.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.finalizarCompra(); // Garantir que o carrinho é limpo corretamente
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
    
    // Endpoint para obter o carrinho de um usuário
    @GetMapping("/{email}/carrinho/retorno")
    public ResponseEntity<?> getCarrinho(@PathVariable String email) {
        String emailWithoutQuotes = email.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getUserCarrinho());
    }
    
    // Endpoint para atualizar todo o carrinho
    @PutMapping("/{email}/carrinho")
    public ResponseEntity<UserModel> updateCarrinho(@PathVariable String email, @RequestBody List<String> novoCarrinho) {
        String emailWithoutQuotes = email.replace("\"", "");
        UserModel user = userRepository.findByEmail(emailWithoutQuotes).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setUserCarrinho(novoCarrinho);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }



    // Endpoint para atualizar os dados do usuário
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id, @RequestBody UserModel updatedUser) {
        UserModel user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualizando os dados do usuário
        user.setNome(updatedUser.getNome());
        user.setEmail(updatedUser.getEmail());
        user.setCpf(updatedUser.getCpf());
        user.setDataNascimento(updatedUser.getDataNascimento());
        user.setGenero(updatedUser.getGenero());
        user.setEnderecoFaturamento(updatedUser.getEnderecoFaturamento());
        user.setEnderecosEntrega(updatedUser.getEnderecosEntrega());
        user.setEnderecoEntregaPadrao(updatedUser.getEnderecoEntregaPadrao());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());

        userRepository.save(user); // Salvando as mudanças

        return ResponseEntity.ok(user);
    }
    
    // Endpoint para obter todos os usuários
    @GetMapping("/")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no users are found
        }
        return ResponseEntity.ok(users); // Return 200 with the list of users
    }
}
