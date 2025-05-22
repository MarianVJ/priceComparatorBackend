package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.entity.StoreDiscountDateBatchProduct;

import java.util.List;

public interface StoreDiscountDateBatchProductService {

    List<StoreDiscountDateBatchProduct> findAll();

    StoreDiscountDateBatchProduct findById(long theId);

    StoreDiscountDateBatchProduct save(StoreDiscountDateBatchProduct theStoreDiscountDateProductBatch);

    void deleteById(long theId);
    Long count();
}
