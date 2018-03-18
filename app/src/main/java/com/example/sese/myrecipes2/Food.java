package com.example.sese.myrecipes2;

public class Food {
    private int key;
    private String title, description;
    public Food(){
    }

    public Food(int key, String title, String description) {
        this.key = key;
        this.title = title;
        this.description = description;
    }

    public int getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setKey(int id){
        this.key = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
