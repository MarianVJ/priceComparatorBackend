package com.example.priceComparatorBackend.service.features;

public interface EmailService {

    void sendPriceAlertEmail(String toEmail, String productName,
                             double currentPrice, double targetPrice);
}
