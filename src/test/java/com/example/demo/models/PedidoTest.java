package com.example.demo.model;

import com.example.demo.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoTest {
    
    private Pedido pedido;
    private Game game1, game2;
    
    @BeforeEach
    public void setUp() {
        pedido = new Pedido();
        game1 = new Game("Game 1", "Action", "Description 1", 29.99, List.of("url1.jpg"), 0);
        game2 = new Game("Game 2", "RPG", "Description 2", 39.99, List.of("url2.jpg"), 0);
        
        pedido.setFrete(10.0);
        pedido.setEnderecoEntrega("Rua Teste, 123");
        pedido.setFormaPagamento("Cartão");
        pedido.setItens(List.of(game1, game2));
        pedido.setUserId("user123");
        pedido.setStatus("Pendente");
        pedido.setTotal(79.98);
        pedido.setData(LocalDateTime.now());
        System.out.println("📦 Setup PedidoTest executado");
    }
    
    @Test
    public void testPedidoCreation() {
        assertNotNull(pedido);
        assertEquals(10.0, pedido.getFrete(), 0.01);
        assertEquals("Rua Teste, 123", pedido.getEnderecoEntrega());
        assertEquals("Cartão", pedido.getFormaPagamento());
        assertEquals("user123", pedido.getUserId());
        assertEquals("Pendente", pedido.getStatus());
        assertEquals(79.98, pedido.getTotal(), 0.01);
        assertNotNull(pedido.getData());
        System.out.println("✅ Criação de pedido testada - Total: R$" + pedido.getTotal());
    }
    
    @Test
    public void testItens() {
        assertEquals(2, pedido.getItens().size());
        assertTrue(pedido.getItens().contains(game1));
        assertTrue(pedido.getItens().contains(game2));
        System.out.println("✅ Itens do pedido testados - Quantidade: " + pedido.getItens().size());
    }
    
    @Test
    public void testPedidoSetters() {
        pedido.setFrete(15.0);
        pedido.setStatus("Entregue");
        pedido.setTotal(89.98);
        
        assertEquals(15.0, pedido.getFrete(), 0.01);
        assertEquals("Entregue", pedido.getStatus());
        assertEquals(89.98, pedido.getTotal(), 0.01);
        System.out.println("✅ Setters de pedido testados - Status: " + pedido.getStatus());
    }
    
    @Test
    public void testCalculoTotal() {
        double expectedTotal = game1.getPrice() + game2.getPrice() + pedido.getFrete();
        pedido.setTotal(expectedTotal);
        
        assertEquals(expectedTotal, pedido.getTotal(), 0.01);
        assertTrue(pedido.getTotal() > 0);
        System.out.println("✅ Cálculo do total testado - Valor: R$" + pedido.getTotal());
    }
    
    @Test
    public void testStatusFlow() {
        String[] statusSequence = {"Pendente", "Confirmado", "Enviado", "Entregue"};
        
        for (String status : statusSequence) {
            pedido.setStatus(status);
            assertEquals(status, pedido.getStatus());
        }
        System.out.println("✅ Fluxo de status testado - Status final: " + pedido.getStatus());
    }
}