package com.example.priceComparatorBackend.service.features;

import com.example.priceComparatorBackend.dao.database.ProductRepository;
import com.example.priceComparatorBackend.dao.features.PriceAlertRepository;
import com.example.priceComparatorBackend.dto.PriceAlertRequestDto;
import com.example.priceComparatorBackend.entity.PriceAlert;
import com.example.priceComparatorBackend.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class CustomPriceAlertServiceImpl implements CustomPriceAlertService {

    private final PriceAlertRepository priceAlertRepository;
    private final ProductRepository productRepository;

    public CustomPriceAlertServiceImpl(
            PriceAlertRepository priceAlertRepository,
            ProductRepository productRepository) {
        this.priceAlertRepository = priceAlertRepository;
        this.productRepository = productRepository;
    }


    public boolean saveAlert(PriceAlertRequestDto dto) {

        Product product =
                productRepository.findByNameAndBrand(dto.getProductName(),
                        dto.getProductBrand());
        if(product == null){
            return false;
        }

        PriceAlert priceAlert = new PriceAlert(product, dto.getTargetPrice(),
                dto.getEmail());

        if(priceAlertRepository.save(priceAlert)!=null)
            return true;
        else
            return false;


    }
}
