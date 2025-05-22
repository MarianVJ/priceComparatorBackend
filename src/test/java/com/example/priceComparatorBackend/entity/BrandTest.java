package com.example.priceComparatorBackend.entity;

import com.example.priceComparatorBackend.dao.database.BrandRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import jakarta.persistence.PersistenceException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BrandTest {

    @Autowired
    private BrandRepository brandRepository;

    private Brand brand;

    @BeforeEach
    public void setUp() {
        // Create a brand object before each test
        brand = new Brand("Agricola");
    }

    @Test
    public void testBrandEntityMapping() {
        // Save Brand Entity
        Brand savedBrand = brandRepository.save(brand);

        // Verify if the id was generated and it was not nul
        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo("Agricola");
    }

    @Test
    public void testFindByName() {

        brandRepository.save(brand);
        Optional<Brand> foundBrand = brandRepository.findByName("Agricola");
        assertThat(foundBrand).isNotNull();
        assertThat(foundBrand.get().getName()).isEqualTo("Agricola");
    }

    @Test
    public void testDeleteBrand() {
        Brand brand = new Brand("Agricola");
        brandRepository.save(brand);

        brandRepository.delete(brand);

        assertThat(brandRepository.findById(brand.getId())).isEmpty();
    }

    @Test
    public void testBrandRepositorySave() {
        // Save a brand in database and verify if it was done with success
        Brand savedBrand = brandRepository.save(brand);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isNotNull();
        assertThat(savedBrand.getName()).isEqualTo("Agricola");
    }

    @Test
    public void testBrandUniqueNameConstraint() {
        // Verifiy the uniqueness
        Brand brand2 = new Brand("Agricola");

        // Save the entity
        brandRepository.save(brand);

        //Verify if the exception is thrown when a Brand with same name is
        // created
        try {
            brandRepository.save(brand2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}