package com.example.SpectaclesWebShop.Bean;

public class Login {
    private long id;
    private String mailId;
    private String name;
    private String password;
    private String hasRole;

    // no user Found
    public Login() {
        this.id = -1;
        this.mailId = null;
        this.name = null;
        this.password = null;
        this.hasRole = null;
    }

    // when Fetch Data
    public Login(long id, String mailId, String name, String password,String hasRole) {
        this.id = id;
        this.mailId = mailId;
        this.name = name;
        this.password = password;
        this.hasRole = hasRole;
    }

    // Fetch User Details
    public Login(long id, String mailId, String name) {
        this.id = id;
        this.mailId = mailId;
        this.name = name;
        this.hasRole = null;
    }

    public String getHasRole() {
        return hasRole;
    }

    public void setHasRole(String hasRole) {
        this.hasRole = hasRole;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", mailId='" + mailId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
