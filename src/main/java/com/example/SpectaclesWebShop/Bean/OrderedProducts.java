package com.example.SpectaclesWebShop.Bean;

public class OrderedProducts {
       private long id;
       private long p_id;
       private long order_id;
       private int qty;
       private boolean onlyframe;
       private String left_eye_no;
       private String right_eye_no;
       private String glassType;
       private double totalPrice;
       private double gst;
       private double otherTax;
       private double sale;
       private double glassPrice;
       private Products products;

       public OrderedProducts(long id, long p_id, long order_id, int qty, boolean onlyframe, String left_eye_no,
                     String right_eye_no, String glassType, double netPrice, double gst, double otherTax, double sale,
                     double glassPrice) {
              this.id = id;
              this.p_id = p_id;
              this.order_id = order_id;
              this.qty = qty;
              this.onlyframe = onlyframe;
              this.left_eye_no = left_eye_no;
              this.right_eye_no = right_eye_no;
              this.glassType = glassType;
              this.totalPrice = netPrice;
              this.gst = gst;
              this.otherTax = otherTax;
              this.sale = sale;
              this.glassPrice = glassPrice;
       }

       public OrderedProducts() {
       }

       public Products getProducts() {
              return products;
       }

       public double getGlassPrice() {
              return glassPrice;
       }

       public void setGlassPrice(double glassPrice) {
              this.glassPrice = glassPrice;
       }

       public void setProducts(Products products) {
              this.products = products;
       }

       public double getSale() {
              return sale;
       }

       public void setSale(double sale) {
              this.sale = sale;
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public long getP_id() {
              return p_id;
       }

       public void setP_id(long p_id) {
              this.p_id = p_id;
       }

       public long getOrder_id() {
              return order_id;
       }

       public void setOrder_id(long order_id) {
              this.order_id = order_id;
       }

       public int getQty() {
              return qty;
       }

       public void setQty(int qty) {
              this.qty = qty;
       }

       public boolean isOnlyframe() {
              return onlyframe;
       }

       public void setOnlyframe(boolean onlyframe) {
              this.onlyframe = onlyframe;
       }

       public String getLeft_eye_no() {
              return left_eye_no;
       }

       public void setLeft_eye_no(String left_eye_no) {
              this.left_eye_no = left_eye_no;
       }

       public String getRight_eye_no() {
              return right_eye_no;
       }

       public void setRight_eye_no(String right_eye_no) {
              this.right_eye_no = right_eye_no;
       }

       public String getGlassType() {
              return glassType;
       }

       public void setGlassType(String glassType) {
              this.glassType = glassType;
       }

       public double getGst() {
              return gst;
       }

       @Override
       public String toString() {
              return "OrderedProducts [glassType=" + glassType + ", gst=" + gst + ", id=" + id + ", left_eye_no="
                            + left_eye_no + ", onlyframe=" + onlyframe + ", order_id=" + order_id + ", otherTax="
                            + otherTax + ", p_id=" + p_id + ", qty=" + qty + ", right_eye_no=" + right_eye_no
                            + ", totalPrice=" + totalPrice + "]";
       }

       public double getTotalPrice() {
              return totalPrice;
       }

       public void setTotalPrice(double totalPrice) {
              this.totalPrice = totalPrice;
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
