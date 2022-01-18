package com.example.SpectaclesWebShop.ServerResponse;

public class ServerResponse {
    private String message;
    private String token;
    private boolean success;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ServerResponse(String token,String message, boolean success){
        this.message = message;
        this.success = success;
        this.token = token;
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
}
