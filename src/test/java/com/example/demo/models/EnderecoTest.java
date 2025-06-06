package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {
    
    private Endereco endereco;
    
    @BeforeEach
    public void setUp() {
        endereco = new Endereco("12345-678", "Rua das Flores", "123", "Apt 45", "Centro", "São Paulo", "SP");
        System.out.println("🏠 Setup EnderecoTest executado");
    }
    
    @Test
    public void testEnderecoCreation() {
        assertNotNull(endereco);
        assertEquals("12345-678", endereco.getCep());
        assertEquals("Rua das Flores", endereco.getLogradouro());
        assertEquals("123", endereco.getNumero());
        assertEquals("Apt 45", endereco.getComplemento());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("SP", endereco.getUf());
        System.out.println("✅ Criação de endereço testada com sucesso!");
    }
    
    @Test
    public void testEnderecoSetters() {
        endereco.setCep("87654-321");
        endereco.setLogradouro("Av. Paulista");
        endereco.setNumero("456");
        endereco.setComplemento("Sala 10");
        endereco.setBairro("Bela Vista");
        endereco.setCidade("Rio de Janeiro");
        endereco.setUf("RJ");
        
        assertEquals("87654-321", endereco.getCep());
        assertEquals("Av. Paulista", endereco.getLogradouro());
        assertEquals("456", endereco.getNumero());
        assertEquals("Sala 10", endereco.getComplemento());
        assertEquals("Bela Vista", endereco.getBairro());
        assertEquals("Rio de Janeiro", endereco.getCidade());
        assertEquals("RJ", endereco.getUf());
        System.out.println("✅ Setters de endereço testados com sucesso!");
    }
    
    @Test
    public void testCepValidation() {
        // Simula validação de CEP
        String cep = endereco.getCep();
        assertTrue(cep.contains("-"));
        assertEquals(9, cep.length()); // 12345-678 = 9 caracteres
        System.out.println("✅ Validação de CEP testada: " + cep);
    }
    
    @Test
    public void testToString() {
        String result = endereco.toString();
        assertTrue(result.contains("12345-678"));
        assertTrue(result.contains("Rua das Flores"));
        assertTrue(result.contains("São Paulo"));
        System.out.println("✅ ToString de endereço testado: " + result.substring(0, 50) + "...");
    }
}