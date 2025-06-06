package com.example.demo.controllers;

import com.example.demo.models.Game;
import com.example.demo.repositories.GameRepository;
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
public class GameControllerTest {
    
    @Mock
    private GameRepository gameRepository;
    
    @InjectMocks
    private GameController gameController;
    
    private Game game;
    
    @BeforeEach
    public void setUp() {
        game = new Game("Test Game", "Action", "Test Description", 29.99, List.of("url1.jpg"), 0);
        game.setId("game1");
        System.out.println("🎮 Setup GameControllerTest executado");
    }
    
    @Test
    public void testGetAllGames() {
        List<Game> games = List.of(game);
        when(gameRepository.findAll()).thenReturn(games);
        
        List<Game> result = gameController.getAllGames();
        
        assertEquals(1, result.size());
        assertEquals("Test Game", result.get(0).getTitle());
        verify(gameRepository).findAll();
        System.out.println("✅ SUCESSO: Buscar todos os jogos - Quantidade: " + result.size());
    }
    
    @Test
    public void testRegisterGame() {
        when(gameRepository.save(game)).thenReturn(game);
        
        ResponseEntity<Game> response = gameController.registerGame(game);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test Game", response.getBody().getTitle());
        verify(gameRepository).save(game);
        System.out.println("✅ SUCESSO: Jogo registrado - " + response.getBody().getTitle());
    }
    
    @Test
    public void testGetGameById_Found() {
        when(gameRepository.findById("game1")).thenReturn(Optional.of(game));
        
        ResponseEntity<Game> response = gameController.getGameById("game1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Game", response.getBody().getTitle());
        System.out.println("✅ SUCESSO: Jogo encontrado por ID - " + response.getBody().getTitle());
    }
    
    @Test
    public void testGetGameById_NotFound() {
        when(gameRepository.findById("nonexistent")).thenReturn(Optional.empty());
        
        ResponseEntity<Game> response = gameController.getGameById("nonexistent");
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("✅ SUCESSO: Jogo não encontrado tratado corretamente");
    }
    
    @Test
    public void testRegisterMultipleGames() {
        Game game2 = new Game("Game 2", "RPG", "RPG Game", 39.99, List.of("url2.jpg"), 0);
        
        when(gameRepository.save(any(Game.class))).thenReturn(game, game2);
        
        ResponseEntity<Game> response1 = gameController.registerGame(game);
        ResponseEntity<Game> response2 = gameController.registerGame(game2);
        
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        verify(gameRepository, times(2)).save(any(Game.class));
        System.out.println("✅ SUCESSO: Múltiplos jogos registrados");
    }
}