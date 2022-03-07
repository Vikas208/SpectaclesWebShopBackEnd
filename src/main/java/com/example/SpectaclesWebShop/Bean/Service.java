package com.example.SpectaclesWebShop.Bean;

public class Service {
    private long id;
    private String name;
    private String phonenumber;

    public Service(long id, String name, String phonenumber) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
