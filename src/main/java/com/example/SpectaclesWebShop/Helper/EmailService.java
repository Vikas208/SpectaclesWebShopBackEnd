package com.example.SpectaclesWebShop.Helper;

import com.example.SpectaclesWebShop.ServerResponse.OtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    private String otp;
    private String otpDate;


    private void GenerateOtp(){
        String [] numbers = new String [] {"1","2","3","4","5","6","7","8","9","0"};
        String otp = "";
        for(int i=0;i<4;++i){
            otp+=numbers[new Random().nextInt(numbers.length)];
        }
        this.otp = otp;
        this.otpDate= String.valueOf(new Date().getTime());
    }
    public OtpResponse sendMail(String mail){
        try{
            GenerateOtp();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("shreechasamaghar263@gmail.com");
            message.setTo(mail);
            message.setSubject("Spectacles Web Shop");
            message.setText("Otp is : "+this.otp+"\nNote:- Otp Valid For 5 minutes\n\nwith regards\nSpectacles web shop team");
            javaMailSender.send(message);
            return new OtpResponse(this.otp,this.otpDate,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new OtpResponse(null,null,false);
    }
}
