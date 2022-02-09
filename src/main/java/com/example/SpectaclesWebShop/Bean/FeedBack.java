package com.example.SpectaclesWebShop.Bean;

public class FeedBack {
       private long id;
       private long c_id;
       private long p_id;
       private String user;
       private double rating;
       private String feedBack;

       public FeedBack(long id, long c_id, long p_id, String user, double rating, String feedBack) {
              this.id = id;
              this.user = user;
              this.rating = rating;
              this.feedBack = feedBack;
              this.c_id = c_id;
              this.p_id = p_id;
       }

       public FeedBack() {
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

       public String getUser() {
              return user;
       }

       public void setUser(String user) {
              this.user = user;
       }

       public double getRating() {
              return rating;
       }

       public void setRating(double rating) {
              this.rating = rating;
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public String getFeedBack() {
              return feedBack;
       }

       public void setFeedBack(String feedBack) {
              this.feedBack = feedBack;
       }

}
