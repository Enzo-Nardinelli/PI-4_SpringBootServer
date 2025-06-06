package com.example.demo.controllers;

import com.example.demo.model.Pedido;
import com.example.demo.models.Game;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.PedidoRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {
    
    @Mock
    private PedidoRepository pedidoRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private PedidoController pedidoController;
    
    private Pedido pedido;
    private Game game;
    private UserModel user;
    
    @BeforeEach
    public void setUp() {
        game = new Game("Test Game", "Action", "Description", 29.99, List.of("url1.jpg"), 0);
        game.setId("game1");
        
        user = new UserModel();
        user.setId("user1");
        user.setEmail("test@test.com");
        
        pedido = new Pedido();
        pedido.setId("pedido1");
        pedido.setUserId("user1");
        pedido.setFrete(10.0);
        pedido.setEnderecoEntrega("Rua Teste, 123");
        pedido.setFormaPagamento("Cartão");
        pedido.setItens(List.of(game));
        pedido.setStatus("Pendente");
        pedido.setTotal(39.99);
        pedido.setData(LocalDateTime.now());
        System.out.println("📦 Setup PedidoControllerTest executado");
    }
    
    @Test
    public void testListar() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        
        ResponseEntity<List<Pedido>> response = pedidoController.listar();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("pedido1", response.getBody().get(0).getId());
        verify(pedidoRepository).findAll();
        System.out.println("✅ SUCESSO: Listar pedidos - Quantidade: " + response.getBody().size());
    }
    
    @Test
    public void testBuscar_Found() {
        when(pedidoRepository.findById("pedido1")).thenReturn(Optional.of(pedido));
        
        ResponseEntity<Pedido> response = pedidoController.buscar("pedido1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pedido1", response.getBody().getId());
        System.out.println("✅ SUCESSO: Pedido encontrado - ID: " + response.getBody().getId());
    }
    
    @Test
    public void testBuscar_NotFound() {
        when(pedidoRepository.findById("nonexistent")).thenReturn(Optional.empty());
        
        ResponseEntity<Pedido> response = pedidoController.buscar("nonexistent");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("✅ SUCESSO: Pedido não encontrado tratado corretamente");
    }
    
    @Test
    public void testBuscarPorUsuario() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoRepository.findByUserId("user1")).thenReturn(pedidos);
        
        ResponseEntity<List<Pedido>> response = pedidoController.buscarPorUsuario("user1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).getUserId());
        System.out.println("✅ SUCESSO: Pedidos por usuário - Usuário: " + response.getBody().get(0).getUserId());
    }
    
    @Test
    public void testBuscarPorUsuario_WithQuotes() {
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoRepository.findByUserId("user1")).thenReturn(pedidos);
        
        ResponseEntity<List<Pedido>> response = pedidoController.buscarPorUsuario("\"user1\"");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(pedidoRepository).findByUserId("user1");
        System.out.println("✅ SUCESSO: Busca com aspas removidas - Resultado: " + response.getBody().size());
    }
    
    @Test
    public void testCriar_Success() {
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        
        ResponseEntity<?> response = pedidoController.criar(pedido);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
        verify(pedidoRepository).save(pedido);
        System.out.println("✅ SUCESSO: Pedido criado - Total: R$" + ((Pedido) response.getBody()).getTotal());
    }
    
    @Test
    public void testCriar_Exception() {
        when(pedidoRepository.save(pedido)).thenThrow(new RuntimeException("Database error"));
        
        ResponseEntity<?> response = pedidoController.criar(pedido);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Erro ao salvar pedido"));
        System.out.println("✅ SUCESSO: Erro na criação tratado - Status: " + response.getStatusCode());
    }
    
    @Test
    public void testAtualizarPedido_Success() {
        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setFrete(15.0);
        pedidoAtualizado.setStatus("Entregue");
        pedidoAtualizado.setTotal(44.99);
        pedidoAtualizado.setEnderecoEntrega("Nova Rua, 456");
        pedidoAtualizado.setFormaPagamento("PIX");
        pedidoAtualizado.setItens(List.of(game));
        pedidoAtualizado.setUserId("user1");
        pedidoAtualizado.setData(LocalDateTime.now());
        
        when(pedidoRepository.findById("pedido1")).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        
        ResponseEntity<?> response = pedidoController.atualizarPedido("pedido1", pedidoAtualizado);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(15.0, ((Pedido) response.getBody()).getFrete(), 0.01);
        assertEquals("Entregue", ((Pedido) response.getBody()).getStatus());
        verify(pedidoRepository).save(pedido);
        System.out.println("✅ SUCESSO: Pedido atualizado - Status: " + ((Pedido) response.getBody()).getStatus());
    }
    
    @Test
    public void testAtualizarPedido_NotFound() {
        Pedido pedidoAtualizado = new Pedido();
        when(pedidoRepository.findById("nonexistent")).thenReturn(Optional.empty());
        
        ResponseEntity<?> response = pedidoController.atualizarPedido("nonexistent", pedidoAtualizado);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(pedidoRepository, never()).save(any());
        System.out.println("✅ SUCESSO: Atualização de pedido inexistente rejeitada");
    }
    
    @Test
    public void testBuscarPedidosPorEmail_Success() {
        List<Pedido> pedidos = List.of(pedido);
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(pedidoRepository.findByUserId("user1")).thenReturn(pedidos);
        
        ResponseEntity<List<Pedido>> response = pedidoController.buscarPedidosPorEmail("test@test.com");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).getUserId());
        System.out.println("✅ SUCESSO: Pedidos por email - Email: test@test.com");
    }
    
    @Test
    public void testBuscarPedidosPorEmail_UserNotFound() {
        when(userRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());
        
        ResponseEntity<List<Pedido>> response = pedidoController.buscarPedidosPorEmail("nonexistent@test.com");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("✅ SUCESSO: Email não encontrado tratado corretamente");
    }
    
    @Test
    public void testFluxoCompletoPedido() {
        // Teste do fluxo completo: criar → buscar → atualizar status
        when(pedidoRepository.save(any())).thenReturn(pedido);
        when(pedidoRepository.findById("pedido1")).thenReturn(Optional.of(pedido));
        
        // 1. Criar pedido
        ResponseEntity<?> createResponse = pedidoController.criar(pedido);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        
        // 2. Buscar pedido
        ResponseEntity<Pedido> findResponse = pedidoController.buscar("pedido1");
        assertEquals(HttpStatus.OK, findResponse.getStatusCode());
        
        // 3. Atualizar status
        pedido.setStatus("Entregue");
        ResponseEntity<?> updateResponse = pedidoController.atualizarPedido("pedido1", pedido);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        
        System.out.println("✅ SUCESSO: Fluxo completo de pedido testado - Status final: Entregue");
    }
}