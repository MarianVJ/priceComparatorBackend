package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductPriceDTO;

import java.time.LocalDate;
import java.util.List;

public interface BasketQueryRepository {
     List<ProductPriceDTO> findCheapestProductsByStore(
             List<String> productNames, LocalDate shoppingDate);
}
