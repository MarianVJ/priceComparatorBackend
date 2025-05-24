package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.features.BestBuysPerUnitRepository;
import com.example.priceComparatorBackend.dao.features.BestPercentageDiscountsRepository;
import com.example.priceComparatorBackend.dto.BestDealsProductDto;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BestBuysPerUnitServiceImpl implements BestBuysPerUnitService {

    private final BestBuysPerUnitRepository bestBuysPerUnitRepository;

    @Autowired
    public BestBuysPerUnitServiceImpl(
            BestBuysPerUnitRepository bestBuysPerUnitRepository) {
        this.bestBuysPerUnitRepository =
                bestBuysPerUnitRepository;
    }


    public     List<BestDealsProductDto> getBestBuysPerUnit(
            LocalDate theDate){

        return bestBuysPerUnitRepository.getBestBuysPerUnit(theDate);
    }
}
