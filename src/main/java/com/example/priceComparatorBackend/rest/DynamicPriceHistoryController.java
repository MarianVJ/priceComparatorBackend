package com.example.priceComparatorBackend.rest;

import com.example.priceComparatorBackend.dao.features.DynamicPriceHistoryRepository;
import com.example.priceComparatorBackend.dto.BasketOptimizationRequest;
import com.example.priceComparatorBackend.dto.BasketOptimizationResponse;
import com.example.priceComparatorBackend.dto.ProductPriceHistoryDto;
import com.example.priceComparatorBackend.service.features.DynamicPriceHistoryService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/dynamic-price-history")
public class DynamicPriceHistoryController {

    DynamicPriceHistoryService dynamicPriceHistoryService;

    @Autowired
    public DynamicPriceHistoryController(DynamicPriceHistoryService dynamicPriceHistoryService){
        this.dynamicPriceHistoryService = dynamicPriceHistoryService;
    }

    @JsonView(ProductPriceHistoryDto.Views.CategoryView.class)
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductPriceHistoryDto>> getDynamicPriceHistoryRepositoryByCategory(
            @PathVariable String categoryName) {


        List<ProductPriceHistoryDto> response =
                dynamicPriceHistoryService.getDynamicPriceHistoryRepositoryByCategory(categoryName);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @JsonView(ProductPriceHistoryDto.Views.BrandView.class)
    @GetMapping("/brand/{brandName}")
    public ResponseEntity<List<ProductPriceHistoryDto>> getDynamicPriceHistoryRepositoryByBrand(
            @PathVariable String brandName) {


        List<ProductPriceHistoryDto> response =
                dynamicPriceHistoryService.  getDynamicPriceHistoryRepositoryByBrand(brandName);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
