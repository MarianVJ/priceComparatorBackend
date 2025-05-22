package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.entity.StoreDateBatch;

import java.util.List;

public interface StoreDateBatchService {

    List<StoreDateBatch> findAll();

    StoreDateBatch findById(long theId);

    StoreDateBatch save(StoreDateBatch theStoreDateBatch);

    void deleteById(long theId);
}
