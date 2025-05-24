package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import com.example.priceComparatorBackend.dto.ProductPriceHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface DynamicPriceHistoryRepository {

    List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByCategory(
            String category);

    List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByBrand(
            String brand);
}
