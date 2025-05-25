package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StoreRepositoryTest {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreRepositoryTest(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store("Lidl");
    }

    @Test
    public void testSaveStore() {
        Store saved = storeRepository.save(store);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Lidl");
    }

    @Test
    public void testFindById() {
        Store saved = storeRepository.save(store);
        Optional<Store> found = storeRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Lidl");
    }

    @Test
    public void testDeleteStore() {
        Store saved = storeRepository.save(store);
        storeRepository.delete(saved);
        Optional<Store> deleted = storeRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
