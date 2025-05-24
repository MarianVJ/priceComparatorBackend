package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.BestDealsProductDto;

import java.time.LocalDate;
import java.util.List;

public interface BestBuysPerUnitRepository {

    List<BestDealsProductDto> getBestBuysPerUnit(
            LocalDate theDate);
}
