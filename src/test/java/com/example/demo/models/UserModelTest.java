package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {
    
    private UserModel user;
    
    @BeforeEach
    public void setUp() {
        user = new UserModel();
        user.setNome("João Silva");
        user.setEmail("joao@teste.com");
        user.setUsername("joao123");
        user.setPassword("senha123");
    }
    
    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals("João Silva", user.getNome());
        assertEquals("joao@teste.com", user.getEmail());
        assertEquals("joao123", user.getUsername());
        System.out.println("✅ Criação de usuário testada!");
    }
    
    @Test
    public void testAddToCarrinho() {
        user.addToCarrinho("game1");
        user.addToCarrinho("game2");
        
        assertEquals(2, user.getCarrinho().size());
        assertTrue(user.getCarrinho().contains("game1"));
        assertTrue(user.getCarrinho().contains("game2"));
        System.out.println("✅ Adicionar ao carrinho testado!");
    }
    
    @Test
    public void testRemoveFromCarrinho() {
        user.addToCarrinho("game1");
        user.addToCarrinho("game2");
        user.removeFromCarrinho("game1");
        
        assertEquals(1, user.getCarrinho().size());
        assertFalse(user.getCarrinho().contains("game1"));
        assertTrue(user.getCarrinho().contains("game2"));
        System.out.println("✅ Remover do carrinho testado!");
    }
    
    @Test
    public void testFinalizarCompra() {
        user.addToCarrinho("game1");
        user.addToCarrinho("game2");
        
        int carrinhoSizeAntes = user.getCarrinho().size();
        user.finalizarCompra();
        
        assertEquals(0, user.getCarrinho().size());
        assertEquals(carrinhoSizeAntes, user.getJogos().size());
        assertTrue(user.getJogos().contains("game1"));
        assertTrue(user.getJogos().contains("game2"));
        System.out.println("✅ Finalizar compra testado!");
    }
    
    @Test
    public void testSetUserCarrinho() {
        java.util.List<String> novoCarrinho = java.util.List.of("game3", "game4");
        user.setUserCarrinho(novoCarrinho);
        
        assertEquals(2, user.getUserCarrinho().size());
        assertTrue(user.getUserCarrinho().contains("game3"));
        assertTrue(user.getUserCarrinho().contains("game4"));
        System.out.println("✅ Set carrinho testado!");
    }
    
    @Test
    public void testIsEstoquista() {
        assertFalse(user.getIsEstoquista());
        user.setIsEstoquista(true);
        assertTrue(user.getIsEstoquista());
        System.out.println("✅ Estoquista testado!");
    }
}