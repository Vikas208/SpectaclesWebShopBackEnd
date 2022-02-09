package com.example.SpectaclesWebShop.Bean;

// Common Class For Category FrameStyle Compant Name
public class Data {
       private long id;
       private String data;

       public Data(long l, String data) {
              this.id = l;
              this.data = data;
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public String getData() {
              return data;
       }

       public void setData(String data) {
              this.data = data;
       }
}
