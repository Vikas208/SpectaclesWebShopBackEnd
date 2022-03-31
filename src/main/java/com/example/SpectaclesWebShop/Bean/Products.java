package com.example.SpectaclesWebShop.Bean;

public class Products {
    private long id;
    private String p_name;
    private double p_price;
    private int TotalSales;
    private int p_stock;
    private Double Rating;
    private ProductDescription productDescription;
    private ProductSales productSales;
    private String BannerImage;

    public Products() {
    }

    public Products(long id, String p_name, double p_price, int totalSales, int p_stock, Double rating,
            ProductDescription productDescription, String BannerImage, ProductSales productSales) {
        this.id = id;
        this.p_name = p_name;
        this.p_price = p_price;
        this.TotalSales = totalSales;
        this.p_stock = p_stock;
        this.Rating = rating;
        this.productDescription = productDescription;
        this.BannerImage = BannerImage;
        this.productSales = productSales;
    }

    public ProductSales getProductSales() {
        return productSales;
    }

    public void setProductSales(ProductSales productSales) {
        this.productSales = productSales;
    }

    public String getBannerImage() {
        return BannerImage;
    }

    public void setBannerImage(String bannerImage) {
        BannerImage = bannerImage;
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public double getP_price() {
        return p_price;
    }

    public void setP_price(double p_price) {
        this.p_price = p_price;
    }

    @Override
    public String toString() {
        return "Products [BannerImage=" + BannerImage + ", Rating=" + Rating + ", TotalSales=" + TotalSales + ", id="
                + id + ", p_name=" + p_name + ", p_price=" + p_price + ", p_stock=" + p_stock + ", productDescription="
                + productDescription + ", productSales=" + productSales + "]";
    }

}
