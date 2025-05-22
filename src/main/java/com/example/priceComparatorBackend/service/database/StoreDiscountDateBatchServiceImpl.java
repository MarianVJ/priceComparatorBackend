package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.dao.database.StoreDiscountDateBatchRepository;
import com.example.priceComparatorBackend.entity.Store;
import com.example.priceComparatorBackend.entity.StoreDiscountDateBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StoreDiscountDateBatchServiceImpl
        implements StoreDiscountDateBatchService {

    private StoreDiscountDateBatchRepository storeDiscountDateBatchRepository;

    @Autowired
    public StoreDiscountDateBatchServiceImpl(
            StoreDiscountDateBatchRepository theStoreDiscountDateBatchRepository) {
        storeDiscountDateBatchRepository = theStoreDiscountDateBatchRepository;
    }

    @Override
    public List<StoreDiscountDateBatch> findAll() {
        return storeDiscountDateBatchRepository.findAll();
    }


    @Override
    public StoreDiscountDateBatch findById(long theId) {
        Optional<StoreDiscountDateBatch> result =
                storeDiscountDateBatchRepository.findById(theId);
        StoreDiscountDateBatch theStoreDiscountDateBatchRepository = null;

        if (result.isPresent()) {
            theStoreDiscountDateBatchRepository = result.get();
        } else {
            throw new RuntimeException(
                    "Did not find StoreProduct id  - " + theId);
        }
        return theStoreDiscountDateBatchRepository;
    }

    @Override
    public StoreDiscountDateBatch save(
            StoreDiscountDateBatch theStoreDiscountDateBatchRepository) {
        if (theStoreDiscountDateBatchRepository.isValidDateRange())
            return storeDiscountDateBatchRepository.save(
                    theStoreDiscountDateBatchRepository);
        else {
            throw new IllegalArgumentException("Invalid Dates: fromDate " +
                    "needs to be before toDate");
        }
    }

    @Override
    public void deleteById(long theId) {
        storeDiscountDateBatchRepository.deleteById(theId);
    }

    @Override
    public Optional<StoreDiscountDateBatch> findByStoreAndFromDateAndToDate(Store store,
                                                              LocalDate fromDate, LocalDate toDate){
        return storeDiscountDateBatchRepository.findByStoreAndFromDateAndToDate(store, fromDate, toDate);
    }
}
