package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Store;
import com.example.priceComparatorBackend.entity.StoreDateBatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StoreDateBatchRepositoryTest {

    private final StoreDateBatchRepository storeDateBatchRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreDateBatchRepositoryTest(StoreDateBatchRepository storeDateBatchRepository,
                                        StoreRepository storeRepository) {
        this.storeDateBatchRepository = storeDateBatchRepository;
        this.storeRepository = storeRepository;
    }

    private Store store;
    private StoreDateBatch storeDateBatch;

    @BeforeEach
    void setUp() {
        store = storeRepository.save(new Store("Profi"));
        storeDateBatch = new StoreDateBatch();
        storeDateBatch.setStore(store);
        storeDateBatch.setBatchDate(LocalDate.of(2025, 5, 1));
    }

    @Test
    public void testSaveStoreDateBatch() {
        StoreDateBatch saved = storeDateBatchRepository.save(storeDateBatch);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getStore().getName()).isEqualTo("Profi");
        assertThat(saved.getBatchDate()).isEqualTo(LocalDate.of(2025, 5, 1));
    }

    @Test
    public void testFindById() {
        StoreDateBatch saved = storeDateBatchRepository.save(storeDateBatch);
        Optional<StoreDateBatch> found = storeDateBatchRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStore().getName()).isEqualTo("Profi");
    }

    @Test
    public void testDeleteStoreDateBatch() {
        StoreDateBatch saved = storeDateBatchRepository.save(storeDateBatch);
        storeDateBatchRepository.delete(saved);
        Optional<StoreDateBatch> deleted = storeDateBatchRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
