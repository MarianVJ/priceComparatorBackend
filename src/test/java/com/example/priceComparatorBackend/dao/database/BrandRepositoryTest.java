package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class BrandRepositoryTest {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandRepositoryTest(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand("Agricola");
    }

    @Test
    public void testSaveBrand() {
        Brand saved = brandRepository.save(brand);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Agricola");
    }

    @Test
    public void testFindByName() {
        brandRepository.save(brand);
        Optional<Brand> found = brandRepository.findByName("Agricola");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Agricola");
    }

    @Test
    public void testDeleteBrand() {
        Brand saved = brandRepository.save(brand);
        brandRepository.delete(saved);
        Optional<Brand> deleted = brandRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    public void testUniqueNameConstraint() {
        brandRepository.save(brand);
        Brand duplicate = new Brand("Agricola");
        assertThatThrownBy(() -> brandRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
