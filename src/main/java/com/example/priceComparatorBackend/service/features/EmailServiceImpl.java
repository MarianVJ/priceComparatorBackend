package com.example.priceComparatorBackend.service.features;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPriceAlertEmail(String toEmail, String productName, double currentPrice, double targetPrice) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Price Alert: " + productName);
        message.setText("Good news! The price for product '" + productName + "' has dropped to " + currentPrice +
                ", which is below your target price of " + targetPrice + ".");

        mailSender.send(message);
    }
}
