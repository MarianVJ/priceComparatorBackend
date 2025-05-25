package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class StoreDateBatchProductRepositoryTest {

    private final StoreDateBatchProductRepository storeDateBatchProductRepository;
    private final StoreDateBatchRepository storeDateBatchRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public StoreDateBatchProductRepositoryTest(StoreDateBatchProductRepository storeDateBatchProductRepository,
                                               StoreDateBatchRepository storeDateBatchRepository,
                                               StoreRepository storeRepository,
                                               ProductRepository productRepository,
                                               BrandRepository brandRepository,
                                               CategoryRepository categoryRepository) {
        this.storeDateBatchProductRepository = storeDateBatchProductRepository;
        this.storeDateBatchRepository = storeDateBatchRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    private Store store;
    private StoreDateBatch storeDateBatch;
    private Brand brand;
    private Category category;
    private Product product;
    private StoreDateBatchProduct storeDateBatchProduct;

    @BeforeEach
    void setUp() {
        store = storeRepository.save(new Store("Kaufland"));
        storeDateBatch = new StoreDateBatch();
        storeDateBatch.setStore(store);
        storeDateBatch.setBatchDate(LocalDate.of(2025, 5, 1));
        storeDateBatch = storeDateBatchRepository.save(storeDateBatch);

        brand = brandRepository.save(new Brand("Pilos"));
        category = categoryRepository.save(new Category("Panificatie"));

        product = new Product();
        product.setId((long)1);
        product.setName("Paine 500g");
        product.setPackageQuantity(0.5);
        product.setPackageUnit("kg");
        product.setBrand(brand);
        product.setCategory(category);
        product = productRepository.save(product);

        storeDateBatchProduct = new StoreDateBatchProduct(storeDateBatch, product, 5.5, "RON");
    }

    @Test
    public void testSaveStoreDateBatchProduct() {
        StoreDateBatchProduct saved = storeDateBatchProductRepository.save(storeDateBatchProduct);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPrice()).isEqualTo(5.5);
        assertThat(saved.getCurrency()).isEqualTo("RON");
        assertThat(saved.getProduct().getName()).isEqualTo("Paine 500g");
        assertThat(saved.getStoreDateBatch().getBatchDate()).isEqualTo(LocalDate.of(2025, 5, 1));
    }

    @Test
    public void testFindById() {
        StoreDateBatchProduct saved = storeDateBatchProductRepository.save(storeDateBatchProduct);
        Optional<StoreDateBatchProduct> found = storeDateBatchProductRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getPrice()).isEqualTo(5.5);
    }

    @Test
    public void testDeleteStoreDateBatchProduct() {
        StoreDateBatchProduct saved = storeDateBatchProductRepository.save(storeDateBatchProduct);
        storeDateBatchProductRepository.delete(saved);
        Optional<StoreDateBatchProduct> deleted = storeDateBatchProductRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
