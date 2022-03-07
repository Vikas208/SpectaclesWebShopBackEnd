package com.example.SpectaclesWebShop.Bean;

public class OrderAddress {
       private long id;
       private long order_id;
       private String address1;
       private String address2;
       private String city;
       private String State;
       private String pincode;
       private String phonenumber;

       public OrderAddress(long id, long c_id, String address1, String address2, String city, String state,
                     String pincode, String phonenumber) {
              this.id = id;
              this.order_id = c_id;
              this.address1 = address1;
              this.address2 = address2;
              this.city = city;
              State = state;
              this.pincode = pincode;
              this.phonenumber = phonenumber;
       }

       public String getPhonenumber() {
              return phonenumber;
       }

       public void setPhonenumber(String phonenumber) {
              this.phonenumber = phonenumber;
       }

       public OrderAddress() {
       }

       public long getId() {
              return id;
       }

       public void setId(long id) {
              this.id = id;
       }

       public long getOrder_id() {
              return order_id;
       }

       public void setOrder_id(long Order_id) {
              this.order_id = Order_id;
       }

       public String getAddress1() {
              return address1;
       }

       public void setAddress1(String address1) {
              this.address1 = address1;
       }

       public String getAddress2() {
              return address2;
       }

       public void setAddress2(String address2) {
              this.address2 = address2;
       }

       public String getCity() {
              return city;
       }

       public void setCity(String city) {
              this.city = city;
       }

       public String getState() {
              return State;
       }

       public void setState(String state) {
              State = state;
       }

       public String getPincode() {
              return pincode;
       }

       public void setPincode(String pincode) {
              this.pincode = pincode;
       }

}
