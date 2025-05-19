package com.example.priceComparatorBackend.service;


import com.example.priceComparatorBackend.entity.StoreProduct;

import java.util.List;

public interface StoreProductService {
    List<StoreProduct> findAll();

    StoreProduct findById(long theId);

    StoreProduct save(StoreProduct theStoreProduct);

    void deleteById(long theId);
}
