package com.example.SpectaclesWebShop.Bean;

public class Products {
    private long id;
    private String p_name;
    private String p_description;
    private double p_price;
    private String p_category; // lens,googles
    private String p_group;// men,women,kids
    private String p_frameStyle;
    private String p_bannerImage;
    private int TotalSales;
    private int p_stock;
    private Double Rating;

    public Products() {
    }

    public Products(long id, String p_name, String p_description, double p_price, String p_category, String p_group,
            String p_frameStyle, String p_bannerImage, int p_stock, int TotalSales, Double Rating) {
        this.id = id;
        this.p_name = p_name;
        this.p_description = p_description;
        this.p_price = p_price;
        this.p_category = p_category;
        this.p_group = p_group;
        this.p_frameStyle = p_frameStyle;
        this.p_bannerImage = p_bannerImage;
        this.TotalSales = TotalSales;
        this.Rating = Rating;
        this.p_stock = p_stock;
    }

    public int getP_stock() {
        return p_stock;
    }

    public void setP_stock(int p_stock) {
        this.p_stock = p_stock;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public int getTotalSales() {
        return TotalSales;
    }

    public void setTotalSales(int totalSales) {
        TotalSales = totalSales;
    }

    public long getId() {
        return id;
    }

    public String getP_frameStyle() {
        return p_frameStyle;
    }

    public void setP_frameStyle(String p_frameStyle) {
        this.p_frameStyle = p_frameStyle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public double getP_price() {
        return p_price;
    }

    public void setP_price(double p_price) {
        this.p_price = p_price;
    }

    public String getP_category() {
        return p_category;
    }

    public void setP_category(String p_category) {
        this.p_category = p_category;
    }

    public String getP_group() {
        return p_group;
    }

    public void setP_group(String p_group) {
        this.p_group = p_group;
    }

    public String getP_bannerImage() {
        return p_bannerImage;
    }

    public void setP_bannerImage(String p_bannerImage) {
        this.p_bannerImage = p_bannerImage;
    }
}
