package com.example.priceComparatorBackend.entity;

import com.example.priceComparatorBackend.dao.database.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BrandTest {

    private final BrandRepository brandRepository;
    private Brand brand;

    @Autowired
    public BrandTest(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @BeforeEach
    public void setUp() {
        brand = new Brand("Agricola");
    }

    @Test
    public void testBrandEntityMapping() {
        Brand savedBrand = brandRepository.save(brand);
        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo("Agricola");
    }

    @Test
    public void testFindByName() {
        brandRepository.save(brand);
        Optional<Brand> foundBrand = brandRepository.findByName("Agricola");
        assertThat(foundBrand).isPresent();
        assertThat(foundBrand.get().getName()).isEqualTo("Agricola");
    }

    @Test
    public void testDeleteBrand() {
        brandRepository.save(brand);
        brandRepository.delete(brand);
        assertThat(brandRepository.findById(brand.getId())).isEmpty();
    }

    @Test
    public void testBrandRepositorySave() {
        Brand savedBrand = brandRepository.save(brand);
        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo("Agricola");
    }

    @Test
    public void testBrandUniqueNameConstraint() {
        Brand brand2 = new Brand("Agricola");
        brandRepository.save(brand);

        try {
            brandRepository.save(brand2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}
