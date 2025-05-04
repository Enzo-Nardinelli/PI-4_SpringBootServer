package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pedidos")
public class Pedido {

    @Id
    private String id;

    private Double frete;
    private String enderecoEntrega;
    private String formaPagamento;
    private List<ItemPedido> itens;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getFrete() {
        return frete;
    }

    public void setFrete(Double frete) {
        this.frete = frete;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getTotalProdutos() {
        return itens.stream().mapToDouble(i -> i.getPreco() * i.getQuantidade()).sum();
    }

    public Double getTotalGeral() {
        return getTotalProdutos() + (frete != null ? frete : 0);
    }
}
