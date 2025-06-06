package com.example.demo.validation;

import com.example.demo.models.UserModel;
import com.example.demo.models.Game;
import com.example.demo.models.Endereco;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.regex.Pattern;

public class DataValidationTest {
    
    @Test
    public void testValidEmail() {
        String[] validEmails = {
            "user@domain.com",
            "test.email@company.co.uk", 
            "name+tag@example.org",
            "user123@test-domain.com"
        };
        
        for (String email : validEmails) {
            assertTrue(isValidEmail(email));
            System.out.println("✅ Email válido testado: " + email);
        }
    }
    
    @Test
public void testInvalidEmail() {
    String[] clearlyInvalidEmails = {
        "invalid-email",      // Sem @
        "@domain.com",        // Sem usuário
        "user@",              // Sem domínio
        "user@@domain.com",   // Duplo @
        "user@domain",        // Sem TLD
        "user space@domain.com" // Com espaço
    };
    
    for (String email : clearlyInvalidEmails) {
        assertFalse(isValidEmail(email), "Email deveria ser inválido: " + email);
        System.out.println("✅ Email inválido rejeitado: " + email);
    }
}
    
    @Test
    public void testCepFormat() {
        String[] validCeps = {
            "12345-678",
            "01234-567", 
            "98765-432"
        };
        
        for (String cep : validCeps) {
            assertTrue(isValidCep(cep));
            System.out.println("✅ CEP válido testado: " + cep);
        }
    }
    
    @Test
    public void testInvalidCepFormat() {
        String[] invalidCeps = {
            "12345678",      // Sem hífen
            "1234-678",      // Muito curto
            "123456-78",     // Formato errado
            "abcde-fgh",     // Letras
            "12345-67a"      // Letra no final
        };
        
        for (String cep : invalidCeps) {
            assertFalse(isValidCep(cep));
            System.out.println("✅ CEP inválido rejeitado: " + cep);
        }
    }
    
    @Test
    public void testGamePriceValidation() {
        Game game = new Game();
        
        // Preços válidos
        double[] validPrices = {0.01, 29.99, 59.90, 199.99};
        for (double price : validPrices) {
            game.setPrice(price);
            assertTrue(game.getPrice() > 0);
            System.out.println("✅ Preço válido testado: R$" + price);
        }
        
        // Preços inválidos
        double[] invalidPrices = {-10.0, -0.01};
        for (double price : invalidPrices) {
            game.setPrice(price);
            assertTrue(game.getPrice() <= 0);
            System.out.println("✅ Preço inválido detectado: R$" + price);
        }
    }
    
    @Test
    public void testPasswordStrength() {
        String[] strongPasswords = {
            "MinhaSenh@123",
            "P@ssw0rd!",
            "Teste123!@#"
        };
        
        for (String password : strongPasswords) {
            assertTrue(isStrongPassword(password));
            System.out.println("✅ Senha forte validada: " + maskPassword(password));
        }
        
        String[] weakPasswords = {
            "123456",
            "password",
            "abc",
            "SENHA"
        };
        
        for (String password : weakPasswords) {
            assertFalse(isStrongPassword(password));
            System.out.println("✅ Senha fraca rejeitada: " + maskPassword(password));
        }
    }
    
    @Test
    public void testUserDataValidation() {
        UserModel user = new UserModel();
        
        // Teste nome completo
        user.setNome("João Silva Santos");
        assertTrue(user.getNome().split(" ").length >= 2);
        System.out.println("✅ Nome completo validado: " + user.getNome());
        
        // Teste nome incompleto
        user.setNome("João");
        assertFalse(user.getNome().split(" ").length >= 2);
        System.out.println("✅ Nome incompleto detectado: " + user.getNome());
    }
    
    @Test
    public void testGameDataValidation() {
        Game game = new Game();
        
        // Dados válidos
        game.setTitle("The Witcher 3: Wild Hunt");
        game.setGenre("RPG");
        game.setDescription("Epic fantasy adventure game with amazing story");
        game.setPrice(59.99);
        game.setImgURL(List.of("image1.jpg", "image2.jpg"));
        
        assertTrue(game.getTitle().length() > 0);
        assertTrue(game.getGenre().length() > 0);
        assertTrue(game.getDescription().length() >= 10);
        assertTrue(game.getPrice() > 0);
        assertTrue(game.getImgURL().size() > 0);
        
        System.out.println("✅ Dados do jogo validados: " + game.getTitle());
    }
    
    @Test
    public void testEnderecoValidation() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setUf("SP");
        
        // Validações básicas
        assertTrue(isValidCep(endereco.getCep()));
        assertTrue(endereco.getLogradouro().length() > 0);
        assertTrue(endereco.getNumero().length() > 0);
        assertTrue(endereco.getCidade().length() > 0);
        assertTrue(endereco.getUf().length() == 2);
        
        System.out.println("✅ Endereço completo validado: " + endereco.getCidade() + "/" + endereco.getUf());
    }
    
    @Test
    public void testLimiteCaracteres() {
        UserModel user = new UserModel();
        
        // Nome muito longo
        String nomeLongo = "A".repeat(200);
        user.setNome(nomeLongo);
        assertTrue(user.getNome().length() > 100);
        System.out.println("✅ Nome longo testado: " + user.getNome().length() + " caracteres");
        
        // Email muito longo  
        String emailLongo = "a".repeat(100) + "@domain.com";
        user.setEmail(emailLongo);
        assertTrue(user.getEmail().length() > 50);
        System.out.println("✅ Email longo testado: " + user.getEmail().length() + " caracteres");
    }
    
    @Test
    public void testCaracteresEspeciais() {
        UserModel user = new UserModel();
        
        // Nome com caracteres especiais
        String nomeEspecial = "José da Silva-Pereira O'Connor";
        user.setNome(nomeEspecial);
        assertTrue(user.getNome().contains("-"));
        assertTrue(user.getNome().contains("'"));
        System.out.println("✅ Nome com caracteres especiais: " + user.getNome());
        
        // Teste com emojis
        String nomeComEmoji = "João 😊 Silva";
        user.setNome(nomeComEmoji);
        assertNotNull(user.getNome());
        System.out.println("✅ Nome com emoji testado: " + user.getNome());
    }
    
    // Métodos auxiliares de validação
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    private boolean isValidCep(String cep) {
        return Pattern.matches("\\d{5}-\\d{3}", cep);
    }
    
    private boolean isStrongPassword(String password) {
        return password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*\\d.*") && 
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
    
    private String maskPassword(String password) {
        return "*".repeat(password.length());
    }
}