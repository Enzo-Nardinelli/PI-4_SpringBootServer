package com.example.demo.controllers;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserController userController;
    
    private UserModel user;
    
    @BeforeEach
    public void setUp() {
        user = new UserModel();
        user.setId("user1");
        user.setEmail("test@test.com");
        user.setNome("Test User");
        System.out.println("👤 Setup UserControllerTest executado");
    }
    
    @Test
    public void testAddToCarrinho_Success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        
        ResponseEntity<UserModel> response = userController.addToCarrinho("test@test.com", "game1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCarrinho().contains("game1"));
        verify(userRepository).save(user);
        System.out.println("✅ SUCESSO: Item adicionado ao carrinho - Tamanho: " + response.getBody().getCarrinho().size());
    }
    
    @Test
    public void testAddToCarrinho_UserNotFound() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        
        ResponseEntity<UserModel> response = userController.addToCarrinho("test@test.com", "game1");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userRepository, never()).save(any());
        System.out.println("✅ SUCESSO: Usuário não encontrado para carrinho");
    }
    
    @Test
    public void testRemoveFromCarrinho_Success() {
        user.addToCarrinho("game1");
        user.addToCarrinho("game2");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        
        ResponseEntity<UserModel> response = userController.removeFromCarrinho("test@test.com", "game1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().getCarrinho().contains("game1"));
        assertTrue(response.getBody().getCarrinho().contains("game2"));
        System.out.println("✅ SUCESSO: Item removido do carrinho - Restam: " + response.getBody().getCarrinho().size());
    }
    
    @Test
    public void testFinalizarCompra_Success() {
        user.addToCarrinho("game1");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        
        ResponseEntity<UserModel> response = userController.finalizarCompra("test@test.com");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getCarrinho().size());
        assertEquals(1, response.getBody().getJogos().size());
        System.out.println("✅ SUCESSO: Compra finalizada - Jogos: " + response.getBody().getJogos().size());
    }
    
    @Test
    public void testGetAllUsers_Success() {
        List<UserModel> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        
        ResponseEntity<List<UserModel>> response = userController.getAllUsers();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test User", response.getBody().get(0).getNome());
        System.out.println("✅ SUCESSO: Todos os usuários listados - Quantidade: " + response.getBody().size());
    }
    
    @Test
    public void testUpdateCarrinho_Success() {
        List<String> novoCarrinho = List.of("game1", "game2", "game3");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        
        ResponseEntity<UserModel> response = userController.updateCarrinho("test@test.com", novoCarrinho);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().getUserCarrinho().size());
        assertTrue(response.getBody().getUserCarrinho().contains("game1"));
        System.out.println("✅ SUCESSO: Carrinho atualizado - Novos itens: " + response.getBody().getUserCarrinho().size());
    }
}