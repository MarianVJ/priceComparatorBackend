package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dto.DateRequestDto;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import com.example.priceComparatorBackend.service.features.BestPercentageDiscountsService;
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
@RequestMapping("/rest/best-discounts")
public class BestPercentageDiscountsController {

    private final BestPercentageDiscountsService bestPercentageDiscountsService;

    @Autowired
    public BestPercentageDiscountsController(
            BestPercentageDiscountsService bestPercentageDiscountsService) {

        this.bestPercentageDiscountsService = bestPercentageDiscountsService;
    }

    @PostMapping("/on-date")
    public ResponseEntity<List<ProductDiscountPercentageDto>> bestPercentageDiscounts(
            @RequestBody
            DateRequestDto request) {


        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        LocalDate requestDate = request.getDate();


        List<ProductDiscountPercentageDto> response =
                bestPercentageDiscountsService.getBestPercentageDiscountsByDate(requestDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
