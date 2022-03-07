package com.example.SpectaclesWebShop.Bean;

public class GlassType {
       private long id;
       private String glass_name;
       private double price;

       public GlassType(long id, String glass_name, double price) {
              this.id = id;
              this.glass_name = glass_name;
              this.price = price;
       }

       public GlassType() {
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public String getGlass_name() {
              return glass_name;
       }

       public void setGlass_name(String glass_name) {
              this.glass_name = glass_name;
       }

       public double getPrice() {
              return price;
       }

       public void setPrice(double price) {
              this.price = price;
       }

}
