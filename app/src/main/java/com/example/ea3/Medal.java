package com.example.ea3;

public class Medal {
    private String title;
    private String description;

    public Medal(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}