package com.example.priceComparatorBackend.service.features;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender,
                            JavaMailSender javaMailSender) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    public void sendPriceAlertEmail(String toEmail, String productName,
                                    double currentPrice, double targetPrice) {


        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("Price Alert " + productName);
            helper.setText("Good news! The price for product '" + productName +
                    "' has dropped to " + currentPrice +
                    ", which is below your target price of " + targetPrice +
                    ".");

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
