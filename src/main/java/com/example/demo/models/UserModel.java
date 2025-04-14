package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class UserModel {

    @Id
    private String id;

    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;
    private String genero;
    private String senha;
    
    private Endereco enderecoFaturamento;
    private List<Endereco> enderecosEntrega = new ArrayList<>();
    private int enderecoEntregaPadrao;  // Índice do endereço de entrega padrão

    private String username;
    private String password;

    private List<String> jogos = new ArrayList<>();
    private List<String> carrinho = new ArrayList<>();

    // Constructors
    public UserModel() {}

    public UserModel(String nome, String email, String cpf, String dataNascimento, 
                     String genero, String senha, Endereco enderecoFaturamento, 
                     List<Endereco> enderecosEntrega, int enderecoEntregaPadrao,
                     String username, String password) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.senha = senha;
        this.enderecoFaturamento = enderecoFaturamento;
        this.enderecosEntrega = enderecosEntrega;
        this.enderecoEntregaPadrao = enderecoEntregaPadrao;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEnderecoFaturamento() {
        return enderecoFaturamento;
    }

    public void setEnderecoFaturamento(Endereco enderecoFaturamento) {
        this.enderecoFaturamento = enderecoFaturamento;
    }

    public List<Endereco> getEnderecosEntrega() {
        return enderecosEntrega;
    }

    public void setEnderecosEntrega(List<Endereco> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
    }

    public int getEnderecoEntregaPadrao() {
        return enderecoEntregaPadrao;
    }

    public void setEnderecoEntregaPadrao(int enderecoEntregaPadrao) {
        this.enderecoEntregaPadrao = enderecoEntregaPadrao;
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

    public List<String> getJogos() {
        return this.jogos;
    }

    public void addJogos(String jogo) {
        jogos.add(jogo);
    }

    public List<String> getCarrinho() {
        return carrinho;
    }

    public void addToCarrinho(String jogo) {
        carrinho.add(jogo);
    }

    public void removeFromCarrinho(String jogo) {
        System.out.println(jogo);
        System.out.println(carrinho);
        carrinho.remove(jogo);
        System.out.println(carrinho);
    }

    public void finalizarCompra() {
        jogos.addAll(carrinho);
        carrinho.clear();
    }
    
    public List<String> getUserCarrinho(){
        return this.carrinho;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", genero='" + genero + '\'' +
                ", enderecoFaturamento=" + enderecoFaturamento +
                ", enderecoEntregaPadrao=" + enderecoEntregaPadrao +
                '}';
    }
}
