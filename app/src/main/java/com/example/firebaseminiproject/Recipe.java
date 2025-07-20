package com.example.firebaseminiproject;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private String id;
    private String name;
    private List<String> ingredients;  // هنا قائمة وليس نص
    private List<String> description;  // هنا قائمة وليس نص
    private String category;
    private String videoUrl;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Recipe(String name, String category, List<String> description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setCategory(String category) {
        this.category = category;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Recipe() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
}

