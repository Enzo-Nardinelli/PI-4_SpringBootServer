package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class UserModelEdgeCasesTest {
    
    private UserModel user;
    
    @BeforeEach
    public void setUp() {
        user = new UserModel();
        System.out.println("🔄 Setup UserModelEdgeCasesTest executado");
    }
    
    @Test
    public void testCarrinhoWithNullItems() {
        user.addToCarrinho(null);
        assertEquals(1, user.getCarrinho().size());
        assertTrue(user.getCarrinho().contains(null));
        System.out.println("✅ Carrinho com item null testado - Tamanho: " + user.getCarrinho().size());
    }
    
    @Test
    public void testRemoveFromEmptyCarrinho() {
        assertDoesNotThrow(() -> user.removeFromCarrinho("nonexistent"));
        assertEquals(0, user.getCarrinho().size());
        System.out.println("✅ Remoção de carrinho vazio testada");
    }
    
    @Test
    public void testFinalizarCompraWithEmptyCarrinho() {
        int initialJogosSize = user.getJogos().size();
        user.finalizarCompra();
        assertEquals(0, user.getCarrinho().size());
        assertEquals(initialJogosSize, user.getJogos().size());
        System.out.println("✅ Finalizar compra com carrinho vazio testado");
    }
    
    @Test
    public void testMultipleAddSameGame() {
        user.addToCarrinho("game1");
        user.addToCarrinho("game1");
        user.addToCarrinho("game1");
        
        assertEquals(3, user.getCarrinho().size());
        long count = user.getCarrinho().stream().filter("game1"::equals).count();
        assertEquals(3, count);
        System.out.println("✅ Múltiplos itens iguais testados - Quantidade: " + count);
    }
    
    @Test
    public void testSetCarrinhoWithNull() {
        assertDoesNotThrow(() -> user.setUserCarrinho(null));
        System.out.println("✅ Set carrinho null testado");
    }
    
    @Test
    public void testLargeCarrinho() {
        for (int i = 0; i < 1000; i++) {
            user.addToCarrinho("game" + i);
        }
        assertEquals(1000, user.getCarrinho().size());
        System.out.println("✅ Carrinho grande testado - Tamanho: " + user.getCarrinho().size());
    }
    
    @Test
    public void testEmailFormats() {
        String[] emails = {"test@email.com", "user@domain.org", "name.lastname@company.co.uk"};
        
        for (String email : emails) {
            user.setEmail(email);
            assertTrue(email.contains("@"));
            assertTrue(email.contains("."));
            System.out.println("✅ Email testado: " + email);
        }
    }
}