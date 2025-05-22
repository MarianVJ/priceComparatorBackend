package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.dao.database.StoreDiscountDateBatchProductRepository;
import com.example.priceComparatorBackend.entity.StoreDiscountDateBatchProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreDiscountDateBatchProductServiceImpl
        implements StoreDiscountDateBatchProductService {

    private StoreDiscountDateBatchProductRepository
            storeDiscountDateBatchProductRepository;

    @Autowired
    public StoreDiscountDateBatchProductServiceImpl(
            StoreDiscountDateBatchProductRepository theStoreDiscountDateBatchProductRepository) {
        storeDiscountDateBatchProductRepository =
                theStoreDiscountDateBatchProductRepository;
    }

    @Override
    public List<StoreDiscountDateBatchProduct> findAll() {
        return storeDiscountDateBatchProductRepository.findAll();
    }


    @Override
    public StoreDiscountDateBatchProduct findById(long theId) {
        Optional<StoreDiscountDateBatchProduct> result =
                storeDiscountDateBatchProductRepository.findById(theId);
        StoreDiscountDateBatchProduct theStoreDiscountDateBatchProduct = null;

        if (result.isPresent()) {
            theStoreDiscountDateBatchProduct = result.get();
        } else {
            throw new RuntimeException(
                    "Did not find StoreProduct id  - " + theId);
        }
        return theStoreDiscountDateBatchProduct;
    }

    @Override
    public StoreDiscountDateBatchProduct save(
            StoreDiscountDateBatchProduct theStoreDiscountDateBatchProduct) {
        return storeDiscountDateBatchProductRepository.save(
                theStoreDiscountDateBatchProduct);
    }

    @Override
    public void deleteById(long theId) {
        storeDiscountDateBatchProductRepository.deleteById(theId);
    }

    @Override
    public Long count() {
        return storeDiscountDateBatchProductRepository.count();
    }
}
