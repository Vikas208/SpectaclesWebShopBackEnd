package com.example.SpectaclesWebShop.Bean;

public class ShopDetails {
    private String shopName;
    private Address address;
    private String phoneNumber;
    private String mailId;

    public ShopDetails(String shopName, Address address, String phoneNumber, String mailId) {
        this.shopName = shopName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.mailId = mailId;
    }

    public ShopDetails() {
    }
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
}
