package com.example.SpectaclesWebShop.Bean;

public class TaxDetails {
    private long id;
    private String category;
    private double gst;
    private double othertax;

    public TaxDetails(long id, String category, double gst, double othertax) {
        this.id = id;
        this.category = category;
        this.gst = gst;
        this.othertax = othertax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getOthertax() {
        return othertax;
    }

    public void setOthertax(double othertax) {
        this.othertax = othertax;
    }
}
