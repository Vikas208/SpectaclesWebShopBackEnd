package com.example.SpectaclesWebShop.Bean;

import java.sql.Date;

public class ProductSales {
       private long ps_id;
       private long p_id;
       private Date sale_start;
       private Date sale_end;
       private double saleOff;
       private double salePercentage;

       public ProductSales(long ps_id, long p_id, Date sale_start, Date sale_end, double saleOff,
                     double salePercentage) {
              this.ps_id = ps_id;
              this.p_id = p_id;
              this.sale_start = sale_start;
              this.sale_end = sale_end;
              this.saleOff = saleOff;
              this.salePercentage = salePercentage;
       }

       public ProductSales() {
       }

       public long getPs_id() {
              return ps_id;
       }

       public void setPs_id(long ps_id) {
              this.ps_id = ps_id;
       }

       public long getP_id() {
              return p_id;
       }

       public void setP_id(long p_id) {
              this.p_id = p_id;
       }

       public Date getSale_start() {
              return sale_start;
       }

       public void setSale_start(Date sale_start) {
              this.sale_start = sale_start;
       }

       public Date getSale_end() {
              return sale_end;
       }

       public void setSale_end(Date sale_end) {
              this.sale_end = sale_end;
       }

       public double getSaleOff() {
              return saleOff;
       }

       public void setSaleOff(double saleOff) {
              this.saleOff = saleOff;
       }

       public double getSalePercentage() {
              return salePercentage;
       }

       public void setSalePercentage(double salePercentage) {
              this.salePercentage = salePercentage;
       }

}
