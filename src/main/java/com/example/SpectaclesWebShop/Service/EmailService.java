package com.example.SpectaclesWebShop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public boolean sendMail(String mail,int otp){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("shreechasamaghar263@gmail.com");
            message.setTo(mail);
            message.setSubject("Spectacles Web Shop");
            message.setText("Otp is : "+otp+"\nNote:- Otp Valid For 5 minutes\n\nwith regards\nSpectacles web shop team");
            javaMailSender.send(message);

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
