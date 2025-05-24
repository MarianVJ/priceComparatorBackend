package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dto.BestDealsProductDto;
import com.example.priceComparatorBackend.dto.DateRequestDto;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import com.example.priceComparatorBackend.service.features.BestBuysPerUnitService;
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
@RequestMapping("/rest/best-deals/")
public class BestBuysPerUnitController {

    private final BestBuysPerUnitService bestBuysPerUnitService;

    @Autowired
    public BestBuysPerUnitController(
            BestBuysPerUnitService bestBuysPerUnitService) {

        this.bestBuysPerUnitService = bestBuysPerUnitService;
    }

    @PostMapping("/on-date")
    public ResponseEntity<List<BestDealsProductDto>> bestPercentageDiscounts(
            @RequestBody
            DateRequestDto request) {




        LocalDate requestDate = request.getDate();

        List<BestDealsProductDto> response =
                bestBuysPerUnitService.getBestBuysPerUnit(requestDate);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}