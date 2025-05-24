package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dto.BestDealsProductDto;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;

import java.time.LocalDate;
import java.util.List;

public interface BestBuysPerUnitService {

    List<BestDealsProductDto> getBestBuysPerUnit(
            LocalDate theDate);
}
