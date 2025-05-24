package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dto.DateRequestDto;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import com.example.priceComparatorBackend.service.features.BestPercentageDiscountsService;
import com.example.priceComparatorBackend.service.features.LatestDiscountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rest/latest-discounts")
public class LatestDiscountsController {

    private final LatestDiscountsService latestDiscountsService;

    @Autowired
    public LatestDiscountsController(
            LatestDiscountsService latestDiscountsService) {

        this.latestDiscountsService = latestDiscountsService;
    }

    @PostMapping("/on-date")
    public ResponseEntity<List<ProductDiscountPercentageDto>> bestPercentageDiscounts(
            @RequestBody
            DateRequestDto request) {




        LocalDate requestDate = request.getDate();


        List<ProductDiscountPercentageDto> response =
                latestDiscountsService.getLatestDiscounts(requestDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}


