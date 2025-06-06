package com.example.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemPedidoTest {
    
    private ItemPedido item;
    
    @BeforeEach
    public void setUp() {
        item = new ItemPedido();
        item.setNome("Game Teste");
        item.setPreco(29.99);
        item.setQuantidade(2);
        System.out.println("🛒 Setup ItemPedidoTest executado");
    }
    
    @Test
    public void testItemPedidoCreation() {
        assertNotNull(item);
        assertEquals("Game Teste", item.getNome());
        assertEquals(29.99, item.getPreco(), 0.01);
        assertEquals(2, item.getQuantidade());
        System.out.println("✅ Criação de item testada - " + item.getNome() + " x" + item.getQuantidade());
    }
    
    @Test
    public void testSubtotal() {
        assertEquals(59.98, item.getSubtotal(), 0.01);
        System.out.println("✅ Subtotal testado - R$" + item.getSubtotal());
    }
    
    @Test
    public void testSubtotalWithZero() {
        item.setQuantidade(0);
        assertEquals(0.0, item.getSubtotal(), 0.01);
        System.out.println("✅ Subtotal com quantidade zero testado - R$" + item.getSubtotal());
    }
    
    @Test
    public void testSetters() {
        item.setNome("Novo Game");
        item.setPreco(19.99);
        item.setQuantidade(3);
        
        assertEquals("Novo Game", item.getNome());
        assertEquals(19.99, item.getPreco(), 0.01);
        assertEquals(3, item.getQuantidade());
        assertEquals(59.97, item.getSubtotal(), 0.01);
        System.out.println("✅ Setters testados - " + item.getNome() + " = R$" + item.getSubtotal());
    }
    
    @Test
    public void testQuantidadeNegativa() {
        item.setQuantidade(-1);
        assertEquals(-1, item.getQuantidade());
        assertTrue(item.getSubtotal() < 0);
        System.out.println("✅ Quantidade negativa testada - Subtotal: R$" + item.getSubtotal());
    }
}