package com.example.SpectaclesWebShop.Bean;

public class Carousel {
    private long id;
    private String images;

    public Carousel() {
    }

    public Carousel(String images, long id) {
        this.images = images;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
