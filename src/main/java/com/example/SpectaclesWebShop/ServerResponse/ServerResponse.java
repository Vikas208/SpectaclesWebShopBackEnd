package com.example.SpectaclesWebShop.ServerResponse;

public class ServerResponse {
    private String message;
    private String token;
    private boolean success;
    private String UserName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ServerResponse(String token, String message, boolean success) {
        this.token = token;
        this.message = message;
        this.success = success;
    }

    public ServerResponse(String token, String UserName, String message, boolean success) {
        this.message = message;
        this.success = success;
        this.token = token;
        this.UserName = UserName;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
