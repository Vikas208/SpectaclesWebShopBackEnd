package com.example.SpectaclesWebShop.ServerResponse;

public class OtpResponse {
    String otp;
    String otpTime;
    boolean success;

    public OtpResponse(String otp, String otpTime, boolean success) {
        this.otp = otp;
        this.otpTime = otpTime;
        this.success = success;
    }

    public String getOtp() {
        return otp;
    }

    public String getOtpTime() {
        return otpTime;
    }

    public boolean isSuccess() {
        return success;
    }
}
