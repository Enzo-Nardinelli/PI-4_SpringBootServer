package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GameTest {
    
    private Game game;
    private List<String> imageUrls;
    
    @BeforeEach
    public void setUp() {
        imageUrls = List.of("url1.jpg", "url2.jpg", "url3.jpg");
        game = new Game("The Witcher 3", "RPG", "Epic fantasy game", 59.99, imageUrls, 0);
    }
    
    @Test
    public void testGameCreation() {
        assertNotNull(game);
        assertEquals("The Witcher 3", game.getTitle());
        assertEquals("RPG", game.getGenre());
        assertEquals("Epic fantasy game", game.getDescription());
        assertEquals(59.99, game.getPrice(), 0.01);
        assertEquals(0, game.getIndexPrincipal());
        System.out.println("✅ Criação de jogo testada!");
    }
    
    @Test
    public void testGameSetters() {
        game.setTitle("New Title");
        game.setGenre("Action");
        game.setDescription("New Description");
        game.setPrice(39.99);
        game.setIndexPrincipal(1);
        
        assertEquals("New Title", game.getTitle());
        assertEquals("Action", game.getGenre());
        assertEquals("New Description", game.getDescription());
        assertEquals(39.99, game.getPrice(), 0.01);
        assertEquals(1, game.getIndexPrincipal());
        System.out.println("✅ Setters de jogo testados!");
    }
    
    @Test
    public void testImageUrls() {
        assertEquals(3, game.getImgURL().size());
        assertTrue(game.getImgURL().contains("url1.jpg"));
        
        List<String> newUrls = List.of("newurl1.jpg", "newurl2.jpg");
        game.setImgURL(newUrls);
        assertEquals(2, game.getImgURL().size());
        assertTrue(game.getImgURL().contains("newurl1.jpg"));
        System.out.println("✅ URLs de imagem testadas!");
    }
}