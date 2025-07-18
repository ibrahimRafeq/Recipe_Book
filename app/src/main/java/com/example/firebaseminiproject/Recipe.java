package com.example.firebaseminiproject;

public class Recipe {
    private int id;
    private String name;
    private String category;
    private String description;

    public Recipe() {

    }

    public Recipe(String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
}

