package com.example.SpectaclesWebShop.Bean;

public class ProductImage {
       private long id;
       private long p_id;
       private String images;

       public ProductImage(long id, long p_id, String imageUrl) {
              this.id = id;
              this.p_id = p_id;
              this.images = imageUrl;
       }

       public ProductImage() {
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

       public String getImages() {
              return images;
       }

       public void setImages(String images) {
              this.images = images;
       }

}
