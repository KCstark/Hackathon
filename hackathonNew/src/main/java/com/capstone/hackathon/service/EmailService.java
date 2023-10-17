package com.capstone.hackathon.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    @Async
    public void sendPasswordResetEmail(String recipientEmail, String resetLink) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(myEmail);
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        message.setText("Click the following link to reset your password:\n" + resetLink);
        emailSender.send(message);
    }
    @Async
    public void confirmationEmail(String recipientEmail, String role, String status) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom(myEmail);
        message.setTo(recipientEmail);
        message.setSubject("Registraion Request Response");
        message.setText("Your Request for "+role+" has been "+status);
        emailSender.send(message);
    }


}

