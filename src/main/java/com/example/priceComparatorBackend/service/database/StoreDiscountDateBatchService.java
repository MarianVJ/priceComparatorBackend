package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.entity.Store;
import com.example.priceComparatorBackend.entity.StoreDiscountDateBatch;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StoreDiscountDateBatchService {

    List<StoreDiscountDateBatch> findAll();

    StoreDiscountDateBatch findById(long theId);

    StoreDiscountDateBatch save(StoreDiscountDateBatch theStoreDiscountDateBatch);

    void deleteById(long theId);

    Optional<StoreDiscountDateBatch> findByStoreAndFromDateAndToDate(Store store, LocalDate fromDate, LocalDate toDate);
}
