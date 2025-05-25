package com.example.priceComparatorBackend.entity;

import com.example.priceComparatorBackend.dao.database.BrandRepository;
import com.example.priceComparatorBackend.dao.database.CategoryRepository;
import com.example.priceComparatorBackend.dao.database.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductTest {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    private Brand brand;
    private Category category;
    private Product product;

    @Autowired
    public ProductTest(ProductRepository productRepository,
                       BrandRepository brandRepository,
                       CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    public void setUp() {
        brand = brandRepository.save(new Brand("Zuzu"));
        category = categoryRepository.save(new Category("Lactate"));

        product = new Product();
        product.setId(1L);
        product.setName("Lapte 1L");
        product.setPackageQuantity(1.0);
        product.setPackageUnit("L");
        product.setBrand(brand);
        product.setCategory(category);
    }

    @Test
    public void testProductEntityMapping() {
        Product saved = productRepository.save(product);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Lapte 1L");
        assertThat(saved.getPackageQuantity()).isEqualTo(1.0);
        assertThat(saved.getPackageUnit()).isEqualTo("L");
        assertThat(saved.getBrand().getName()).isEqualTo("Zuzu");
        assertThat(saved.getCategory().getName()).isEqualTo("Lactate");
    }

    @Test
    public void testFindById() {
        Product saved = productRepository.save(product);
        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Lapte 1L");
    }

    @Test
    public void testDeleteProduct() {
        Product saved = productRepository.save(product);
        productRepository.delete(saved);
        assertThat(productRepository.findById(saved.getId())).isEmpty();
    }
}
