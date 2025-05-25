package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dto.PriceAlertRequestDto;
import com.example.priceComparatorBackend.service.features.CustomPriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/rest/configure-price-alert")
public class CustomPriceAlertController {

    private final CustomPriceAlertService customPriceAlertService;

    @Autowired
    public CustomPriceAlertController(
            CustomPriceAlertService customPriceAlertService) {

        this.customPriceAlertService = customPriceAlertService;
    }

    @PostMapping("/on-product")
    public ResponseEntity<String> bestPercentageDiscounts(
            @RequestBody
            PriceAlertRequestDto request) {


        System.out.println(request.getEmail());

        if (customPriceAlertService.saveAlert(request)) {
            return ResponseEntity.ok("Alertă salvată cu succes!");
        } else {
            return ResponseEntity.internalServerError().build();
        }


    }
}
