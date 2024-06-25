package com.example.ea3;



public class Stadium {
    private String name;
    private int imageResource;
    private String mapUrl;

    public Stadium(String name, int imageResource, String mapUrl) {
        this.name = name;
        this.imageResource = imageResource;
        this.mapUrl = mapUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getMapUrl() {
        return mapUrl;
    }
}
