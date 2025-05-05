package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.demo.models.Game;

import java.util.List;

@Document(collection = "pedidos")
public class Pedido {

    @Id
    private String id;

    private Double frete;
    private String enderecoEntrega;
    private String formaPagamento;
    private List<String> itens;
    //private Liste<double> precos;
    private String userId;

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

    public List<String> getItens() {
        return itens;
    }

    public void setItens(List<String> itens) {
        this.itens = itens;
    }
    
    public String getUserId(){
        return this.userId;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }

    //public Double getTotalProdutos() {
    //return itens.stream()
    //            .mapToDouble(i -> i.getPrice())
    //            .sum();
    //}


    //public Double getTotalGeral() {
    //    return getTotalProdutos() + (frete != null ? frete : 0);
   // }
    
    @Override
    public String toString() {
        return "Pedido{" +
           "id='" + id + '\'' +
           ", frete=" + frete +
           ", enderecoEntrega='" + enderecoEntrega + '\'' +
           ", formaPagamento='" + formaPagamento + '\'' +
           ", itens=" + itens +
           ", userId='" + userId + '\'' +
           '}';
}

}
