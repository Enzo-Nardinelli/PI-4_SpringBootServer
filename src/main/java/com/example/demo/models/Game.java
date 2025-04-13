package com.example.demo.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "games")
public class Game {
    @Id
    private String id;
    private String title;
    private String genre;
    private String description;
    private double price;
    private List<String> imgURL;  // Changed from String to List<String>
    private int indexPrincipal;
    
    // Constructors
    public Game() {}
    
    public Game(String title, String genre, String description, double price, List<String> imgURL, int indexPrincipal) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
        this.indexPrincipal = indexPrincipal;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getIndexPrincipal() {
    return indexPrincipal;
}

public void setIndexPrincipal(int indexPrincipal) {
    this.indexPrincipal = indexPrincipal;
}

    
    public List<String> getImgURL() {
        return this.imgURL;
    }
    
    public void setImgURL(List<String> imgURL) {
        this.imgURL = imgURL;
    }
    
   @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imgURL=" + imgURL +
                ", indPrincipal=" + indexPrincipal +
                '}';
    }
}