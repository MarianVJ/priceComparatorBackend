package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Store;
import com.example.priceComparatorBackend.entity.StoreDiscountDateBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StoreDiscountDateBatchRepositoryTest {

    private final StoreDiscountDateBatchRepository storeDiscountDateBatchRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreDiscountDateBatchRepositoryTest(StoreDiscountDateBatchRepository storeDiscountDateBatchRepository,
                                                StoreRepository storeRepository) {
        this.storeDiscountDateBatchRepository = storeDiscountDateBatchRepository;
        this.storeRepository = storeRepository;
    }

    private Store store;
    private StoreDiscountDateBatch discountDateBatch;

    @BeforeEach
    void setUp() {
        store = storeRepository.save(new Store("Mega Image"));

        discountDateBatch = new StoreDiscountDateBatch();
        discountDateBatch.setStore(store);
        discountDateBatch.setFromDate(LocalDate.of(2025, 5, 1));
        discountDateBatch.setToDate(LocalDate.of(2025, 5, 8));
    }

    @Test
    public void testSaveStoreDiscountDateBatch() {
        StoreDiscountDateBatch saved = storeDiscountDateBatchRepository.save(discountDateBatch);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStore().getName()).isEqualTo("Mega Image");
        assertThat(saved.getFromDate()).isEqualTo(LocalDate.of(2025, 5, 1));
        assertThat(saved.getToDate()).isEqualTo(LocalDate.of(2025, 5, 8));
    }

    @Test
    public void testFindById() {
        StoreDiscountDateBatch saved = storeDiscountDateBatchRepository.save(discountDateBatch);
        Optional<StoreDiscountDateBatch> found = storeDiscountDateBatchRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStore().getName()).isEqualTo("Mega Image");
    }

    @Test
    public void testDeleteStoreDiscountDateBatch() {
        StoreDiscountDateBatch saved = storeDiscountDateBatchRepository.save(discountDateBatch);
        storeDiscountDateBatchRepository.delete(saved);
        Optional<StoreDiscountDateBatch> deleted = storeDiscountDateBatchRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
