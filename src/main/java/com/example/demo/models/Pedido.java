package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.demo.models.Game;
import java.time.LocalDateTime;

import java.util.List;

@Document(collection = "pedidos")
public class Pedido {

    @Id
    private String id;

    private Double frete;
    private String enderecoEntrega;
    private String formaPagamento;
    private List<Game> itens;
    //private Liste<double> precos;
    private String userId;
    private String status;
    private double total;
    private LocalDateTime data;

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

    public List<Game> getItens() {
        return itens;
    }

    public void setItens(List<Game> itens) {
        this.itens = itens;
    }
    
    public String getUserId(){
        return this.userId;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus (String status) {
        this.status = status;
    }
    
    public double getToatal() {
        return this.total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
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
                ", status='" + status + '\'' +
                '}';
}

}
