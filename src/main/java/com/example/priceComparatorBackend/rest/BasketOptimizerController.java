package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dao.features.BasketQueryRepository;
import com.example.priceComparatorBackend.dto.BasketOptimizationRequest;
import com.example.priceComparatorBackend.dto.BasketOptimizationResponse;
import com.example.priceComparatorBackend.dto.ProductPriceDTO;
import com.example.priceComparatorBackend.service.features.BasketQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rest/basket-optimizer")
public class BasketOptimizerController {

    BasketQueryRepository basketQueryRepository;
    BasketQueryService basketQueryService;

    @Autowired
    public BasketOptimizerController(BasketQueryRepository basketQueryRepository, BasketQueryService basketQueryService){
        this.basketQueryRepository = basketQueryRepository;
        this.basketQueryService = basketQueryService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<BasketOptimizationResponse> optimizeBasket(
            @RequestBody BasketOptimizationRequest request) {

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println(request.getDate());

        BasketOptimizationResponse response =
                basketQueryService.findCheapestProductsByStore(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
