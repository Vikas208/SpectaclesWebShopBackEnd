package com.example.SpectaclesWebShop.Bean;

public class CustomersProductsDetails {
       private long id;
       private long c_id;
       private long p_id;
       private int qty;
       private boolean onlyframe;
       private String glassType;
       private double left_eye_no;
       private double right_eye_no;
       private Products products;

       public CustomersProductsDetails(long id, long c_id, long p_id, int qty, boolean onlyframe, double left_eye_no,
                     double right_eye_no,
                     Products products, String glassType) {
              this.id = id;
              this.c_id = c_id;
              this.p_id = p_id;
              this.qty = qty;
              this.onlyframe = onlyframe;
              this.left_eye_no = left_eye_no;
              this.right_eye_no = right_eye_no;
              this.products = products;
              this.glassType = glassType;
       }

       public CustomersProductsDetails() {
       }

       public String getGlassType() {
              return glassType;
       }

       public void setGlassType(String glassType) {
              this.glassType = glassType;
       }

       public boolean isOnlyframe() {
              return onlyframe;
       }

       public void setOnlyframe(boolean onlyframe) {
              this.onlyframe = onlyframe;
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public long getC_id() {
              return c_id;
       }

       public void setC_id(long c_id) {
              this.c_id = c_id;
       }

       public long getP_id() {
              return p_id;
       }

       public void setP_id(long p_id) {
              this.p_id = p_id;
       }

       public int getQty() {
              return qty;
       }

       public void setQty(int qty) {
              this.qty = qty;
       }

       public double getLeft_eye_no() {
              return left_eye_no;
       }

       public void setLeft_eye_no(double left_eye_no) {
              this.left_eye_no = left_eye_no;
       }

       public double getRight_eye_no() {
              return right_eye_no;
       }

       public void setRight_eye_no(double right_eye_no) {
              this.right_eye_no = right_eye_no;
       }

       public Products getProducts() {
              return products;
       }

       public void setProducts(Products products) {
              this.products = products;
       }

}
