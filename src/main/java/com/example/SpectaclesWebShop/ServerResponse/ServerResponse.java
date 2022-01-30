package com.example.SpectaclesWebShop.ServerResponse;

import com.example.SpectaclesWebShop.Bean.Login;

public class ServerResponse {
    private String message;
    private String token;
    private boolean success;
    private Login userDetails;

    public ServerResponse(String token, String message, boolean success) {
        this.token = token;
        this.message = message;
        this.success = success;
    }

    public ServerResponse(String token,Login login, String message, boolean success) {
        this.message = message;
        this.success = success;
        this.token = token;
        this.userDetails = login;
    }

    public ServerResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Login getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Login userDetails) {
        this.userDetails = userDetails;
    }
}
