package com.example.demo.controllers;

import com.example.demo.models.UserModel;
import com.example.demo.models.AdminUser;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.AdminUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private AdminUserRepository adminRepo;
    
    @InjectMocks
    private AuthController authController;
    
    private UserModel user;
    private AdminUser adminUser;
    
    @BeforeEach
    public void setUp() {
        user = new UserModel();
        user.setEmail("test@test.com");
        user.setPassword("password123");
        user.setNome("Test User");
        
        adminUser = new AdminUser("admin", "admin123");
        System.out.println("🔐 Setup AuthControllerTest executado");
    }
    
    @Test
    public void testRegisterUser_Success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        
        ResponseEntity<?> response = authController.registerUser(user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario registrado", response.getBody());
        verify(userRepository).save(user);
        System.out.println("✅ SUCESSO: Registro de usuário testado - Status: " + response.getStatusCode());
    }
    
    @Test
    public void testRegisterUser_EmailExists() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        
        ResponseEntity<?> response = authController.registerUser(user);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userRepository, never()).save(user);
        System.out.println("✅ SUCESSO: Email duplicado rejeitado - Status: " + response.getStatusCode());
    }
    
    @Test
    public void testLoginUser_Success() {
        UserModel existingUser = new UserModel();
        existingUser.setEmail("test@test.com");
        existingUser.setPassword("password123");
        existingUser.setNome("Test User");
        
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));
        
        ResponseEntity<?> response = authController.loginUser(user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody.get("user"));
        System.out.println("✅ SUCESSO: Login testado - Usuário: " + existingUser.getNome());
    }
    
    @Test
    public void testLoginUser_UserNotFound() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        
        ResponseEntity<?> response = authController.loginUser(user);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("✅ SUCESSO: Usuário não encontrado rejeitado - Status: " + response.getStatusCode());
    }
    
    @Test
    public void testLoginUser_WrongPassword() {
        UserModel existingUser = new UserModel();
        existingUser.setEmail("test@test.com");
        existingUser.setPassword("differentPassword");
        
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(existingUser));
        
        ResponseEntity<?> response = authController.loginUser(user);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("✅ SUCESSO: Senha incorreta rejeitada - Status: " + response.getStatusCode());
    }
    
    @Test
    public void testBackofficeLogin_Success() {
        when(adminRepo.findByName("admin")).thenReturn(Optional.of(adminUser));
        
        ResponseEntity<?> response = authController.login(adminUser);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Login bem-sucedido"));
        System.out.println("✅ SUCESSO: Login admin testado - Admin: " + adminUser.getName());
    }
    
    @Test
    public void testBackofficeCadastro_Success() {
        AdminUser newAdmin = new AdminUser("newAdmin", "password");
        when(adminRepo.findByName("newAdmin")).thenReturn(Optional.empty());
        when(adminRepo.save(any(AdminUser.class))).thenReturn(newAdmin);
        
        ResponseEntity<?> response = authController.register(newAdmin);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cadastro realizado com sucesso.", response.getBody());
        verify(adminRepo).save(newAdmin);
        System.out.println("✅ SUCESSO: Cadastro admin testado - Admin: " + newAdmin.getName());
    }
}