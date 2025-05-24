package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;

import java.time.LocalDate;
import java.util.List;

public interface BestPercentageDiscountsRepository {

    List<ProductDiscountPercentageDto> getBestPercentageDiscountsByDate(
            LocalDate todayDate);
}
