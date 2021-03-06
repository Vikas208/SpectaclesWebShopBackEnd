package com.example.SpectaclesWebShop.Bean;

public class Address {
    private String address;
    private String city;
    private String state;
    private String pinCode;

    public Address(String address, String city, String state, String pinCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    public Address() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

}