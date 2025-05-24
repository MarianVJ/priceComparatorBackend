package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dto.ProductPriceHistoryDto;

import java.util.List;

public interface DynamicPriceHistoryService {
    List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByCategory(
            String category);

    List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByBrand(
            String brand);

}
