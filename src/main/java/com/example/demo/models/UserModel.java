package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

@Document(collection = "Users")
public class UserModel {

    @Id
    private String id;

    private String username;

    private String password;

    private List<String> jogos = new ArrayList<>();
    private List<String> carrinho = new ArrayList<>();

    // Constructors
    public UserModel() {}

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getJogos(){
        return this.jogos;
    }

    public void addJogos (String jogo){
        jogos.add(jogo);
    }

    public List<String> getCarrinho(){
        return carrinho;
    }

    public void addToCarrinho(String jogo) {
        carrinho.add(jogo);
    }

    public void removeFromCarrinho(String jogo) {
        carrinho.remove(jogo); // Remove o jogo se ele estiver no carrinho
    }

    public void finalizarCompra() {
        jogos.addAll(carrinho); // Adiciona todos os jogos do carrinho à lista de jogos
        carrinho.clear();       // Esvazia o carrinho
    }

    
    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}