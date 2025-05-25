package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.database.StoreDateBatchProductRepository;
import com.example.priceComparatorBackend.dao.features.PriceAlertRepository;
import com.example.priceComparatorBackend.entity.PriceAlert;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PriceAlertSchedulerServiceImpl
        implements PriceAlertSchedulerService {


    private final PriceAlertRepository priceAlertRepository;
    private final StoreDateBatchProductRepository
            storeDateBatchProductRepository;
    private final EmailService emailService;

    public PriceAlertSchedulerServiceImpl(
            PriceAlertRepository priceAlertRepository,
            StoreDateBatchProductRepository storeDateBatchProductRepository,
            EmailService emailService) {
        this.priceAlertRepository = priceAlertRepository;
        this.storeDateBatchProductRepository = storeDateBatchProductRepository;
        this.emailService = emailService;
    }

    // It runs every 5 minutes / 1 minute
    @Scheduled(fixedRate = 300000 / 5)
    public void checkPriceAlerts() {
        List<PriceAlert> alerts = priceAlertRepository.findAll();


        for (PriceAlert alert : alerts) {
            Long productId = alert.getProduct().getId();


            // In a real-world application, the date would be obtained dynamically.
            // Considering our current database setup, we will use a hard-coded date.
            LocalDate today = LocalDate.parse("2025-05-09");

            // Obtain the current price on any market (including the
            // discounts that exist)
            Double currentPrice =
                    storeDateBatchProductRepository.findEffectivePriceByProductIdAtDate(
                            productId, today);

            if (currentPrice != null &&
                    currentPrice <= alert.getTargetPrice()) {

                // Sends the notification to the user

                emailService.sendPriceAlertEmail(alert.getEmail(),
                        alert.getProduct().getName(),
                        currentPrice, alert.getTargetPrice());


            }
        }
    }
}
