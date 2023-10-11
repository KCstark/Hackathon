package com.capstone.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendPasswordResetEmail(String recipientEmail, String resetLink) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        message.setText("Click the following link to reset your password:\n" + resetLink);
        emailSender.send(message);
    }
    public void confirmationEmail(String recipientEmail, String role, String status) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        message.setText("Your Request for "+role+" has been "+status);
        emailSender.send(message);
    }


}

