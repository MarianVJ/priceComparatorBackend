package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dto.BasketOptimizationRequest;
import com.example.priceComparatorBackend.dto.BasketOptimizationResponse;

public interface BasketQueryService {

    BasketOptimizationResponse findCheapestProductsByStore(BasketOptimizationRequest request);

}
