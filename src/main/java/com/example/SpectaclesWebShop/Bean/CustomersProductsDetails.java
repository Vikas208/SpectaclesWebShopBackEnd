package com.example.SpectaclesWebShop.Bean;

public class CustomersProductsDetails {
       private long id;
       private long common_id; // c_id order_id
       private long p_id;
       private int qty;
       private boolean onlyframe;
       private String glassType;
       private double left_eye_no;
       private double right_eye_no;
       private Products products;

       public CustomersProductsDetails(long id, long common_id, long p_id, int qty, boolean onlyframe,
                     double left_eye_no,
                     double right_eye_no,
                     Products products, String glassType) {
              this.id = id;
              this.common_id = common_id;
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

       public long getcommon_id() {
              return common_id;
       }

       public void setcommon_id(long common_id) {
              this.common_id = common_id;
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

       @Override
       public String toString() {
              return "CustomersProductsDetails [common_id=" + common_id + ", glassType=" + glassType + ", id=" + id
                            + ", left_eye_no=" + left_eye_no + ", onlyframe=" + onlyframe + ", p_id=" + p_id
                            + ", products=" + products + ", qty=" + qty + ", right_eye_no=" + right_eye_no + "]";
       }

}
