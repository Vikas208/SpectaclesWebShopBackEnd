package com.example.SpectaclesWebShop.Bean;

public class ShippingCharge {
       private long id;
       private Double charge;
       private int max;

       public ShippingCharge(long id, Double charge, int max) {
              this.id = id;
              this.charge = charge;
              this.max = max;
       }

       public ShippingCharge() {
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public Double getCharge() {
              return charge;
       }

       public void setCharge(Double charge) {
              this.charge = charge;
       }

       public int getMax() {
              return max;
       }

       public void setMax(int max) {
              this.max = max;
       }

}
