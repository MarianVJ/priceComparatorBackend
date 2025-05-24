package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dto.PriceAlertRequestDto;

public interface CustomPriceAlertService {
    public boolean saveAlert(PriceAlertRequestDto dto);
}
