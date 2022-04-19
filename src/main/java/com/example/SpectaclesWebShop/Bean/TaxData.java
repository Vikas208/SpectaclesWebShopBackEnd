package com.example.SpectaclesWebShop.Bean;

public class TaxData {
    private long id;
    private String categoryName;
    private double gst;
    private double otherTax;

    public TaxData(long id, String categoryName, double gst, double otherTax) {
        this.id = id;
        this.categoryName = categoryName;
        this.gst = gst;
        this.otherTax = otherTax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getOtherTax() {
        return otherTax;
    }

    public void setOtherTax(double otherTax) {
        this.otherTax = otherTax;
    }
}
