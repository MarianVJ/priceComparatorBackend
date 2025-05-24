package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductPriceDto;

import java.time.LocalDate;
import java.util.List;

public interface BasketQueryRepository {
     List<ProductPriceDto> findCheapestProductsByStore(
             List<String> productNames, LocalDate shoppingDate);
}
