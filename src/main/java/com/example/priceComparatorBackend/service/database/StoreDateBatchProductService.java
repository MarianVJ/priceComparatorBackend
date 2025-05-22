package com.example.priceComparatorBackend.service.database;


import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;

import java.util.List;

public interface StoreDateBatchProductService {
    List<StoreDateBatchProduct> findAll();

    StoreDateBatchProduct findById(long theId);

    StoreDateBatchProduct save(StoreDateBatchProduct theStoreDateBatchProduct);

    void deleteById(long theId);

    Long count();
}
