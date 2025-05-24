package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.features.BestPercentageDiscountsRepository;
import com.example.priceComparatorBackend.dao.features.LatestDiscountsRepository;
import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import com.example.priceComparatorBackend.rest.LatestDiscountsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LatestDiscountsServiceImpl implements LatestDiscountsService {

    private final LatestDiscountsRepository latestDiscountsRepository;

    @Autowired
    public LatestDiscountsServiceImpl(
            LatestDiscountsRepository latestDiscountsRepository) {
        this.latestDiscountsRepository =
                latestDiscountsRepository;
    }


    public List<ProductDiscountPercentageDto> getLatestDiscounts(
            LocalDate theDate){

        return latestDiscountsRepository.getLatestDiscounts(theDate);
    }
}
