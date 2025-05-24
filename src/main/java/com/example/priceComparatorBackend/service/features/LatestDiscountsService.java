package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;

import java.time.LocalDate;
import java.util.List;

public interface LatestDiscountsService {

    List<ProductDiscountPercentageDto> getLatestDiscounts(
            LocalDate todayDate);
}
