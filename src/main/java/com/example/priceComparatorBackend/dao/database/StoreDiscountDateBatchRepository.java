package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Store;
import com.example.priceComparatorBackend.entity.StoreDiscountDateBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StoreDiscountDateBatchRepository extends JpaRepository<StoreDiscountDateBatch, Long>{
    Optional<StoreDiscountDateBatch> findByStoreAndFromDateAndToDate(Store store, LocalDate fromDate, LocalDate toDate);
}
