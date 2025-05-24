package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.features.BestPercentageDiscountsRepository;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BestPercentageDiscountsServiceImpl implements BestPercentageDiscountsService {

    private final BestPercentageDiscountsRepository bestPercentageDiscountsRepository;

    @Autowired
    public BestPercentageDiscountsServiceImpl(
            BestPercentageDiscountsRepository bestPercentageDiscountsRepository) {
        this.bestPercentageDiscountsRepository =
                bestPercentageDiscountsRepository;
    }


    public List<ProductDiscountPercentageDto> getBestPercentageDiscountsByDate(
            LocalDate theDate){

        return bestPercentageDiscountsRepository.getBestPercentageDiscountsByDate(theDate);
    }
}
