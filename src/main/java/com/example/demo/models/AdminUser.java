package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AdminUsers")
public class AdminUser {
    
    @Id
    private String id;
    private String name;
    private String password;

    public AdminUser() {}

    public AdminUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters e Setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
