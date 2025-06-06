package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminUserTest {
    
    private AdminUser adminUser;
    
    @BeforeEach
    public void setUp() {
        adminUser = new AdminUser("admin", "admin123");
        System.out.println("👤 Setup AdminUserTest executado");
    }
    
    @Test
    public void testAdminUserCreation() {
        assertNotNull(adminUser);
        assertEquals("admin", adminUser.getName());
        assertEquals("admin123", adminUser.getPassword());
        System.out.println("✅ Criação de usuário admin testada: " + adminUser.getName());
    }
    
    @Test
    public void testAdminUserSetters() {
        adminUser.setName("newAdmin");
        adminUser.setPassword("newPassword");
        
        assertEquals("newAdmin", adminUser.getName());
        assertEquals("newPassword", adminUser.getPassword());
        System.out.println("✅ Setters de admin testados - Novo nome: " + adminUser.getName());
    }
    
    @Test
    public void testEmptyConstructor() {
        AdminUser emptyAdmin = new AdminUser();
        assertNotNull(emptyAdmin);
        assertNull(emptyAdmin.getName());
        assertNull(emptyAdmin.getPassword());
        System.out.println("✅ Construtor vazio de AdminUser testado");
    }
    
    @Test
    public void testIdSetter() {
        adminUser.setId("123");
        assertEquals("123", adminUser.getId());
        System.out.println("✅ ID do admin testado: " + adminUser.getId());
    }
    
    @Test
    public void testPasswordSecurity() {
        String senha = adminUser.getPassword();
        assertNotNull(senha);
        assertTrue(senha.length() >= 6);
        System.out.println("✅ Segurança da senha testada - Tamanho: " + senha.length());
    }
}