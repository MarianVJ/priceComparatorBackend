package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.features.DynamicPriceHistoryRepository;
import com.example.priceComparatorBackend.dto.ProductPriceHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicPriceHistoryServiceImpl implements DynamicPriceHistoryService{

    DynamicPriceHistoryRepository dynamicPriceHistoryRepository;

    @Autowired
    public DynamicPriceHistoryServiceImpl(
            DynamicPriceHistoryRepository dynamicPriceHistoryRepository) {
        this.dynamicPriceHistoryRepository = dynamicPriceHistoryRepository;
    }

    public List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByCategory(
            String category){
        return dynamicPriceHistoryRepository.getDynamicPriceHistoryRepositoryByCategory(category);
    }

    public List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByBrand(
            String brand){
        return  dynamicPriceHistoryRepository.getDynamicPriceHistoryRepositoryByBrand(brand);
    }
}
