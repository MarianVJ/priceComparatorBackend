package com.example.priceComparatorBackend.service.database;


import com.example.priceComparatorBackend.dao.database.BrandRepository;
import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.service.database.BrandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrandServiceImplTest {

    private BrandRepository brandRepository;
    private BrandServiceImpl brandService;

    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        brandService = new BrandServiceImpl(brandRepository);
    }

    @Test
    void testFindAll() {
        Brand b1 = new Brand("Brand1");
        Brand b2 = new Brand("Brand2");

        when(brandRepository.findAll()).thenReturn(Arrays.asList(b1, b2));

        List<Brand> brands = brandService.findAll();

        assertThat(brands).hasSize(2);
        assertThat(brands).containsExactly(b1, b2);
    }

    @Test
    void testFindById_found() {
        Brand b = new Brand("BrandX");
        b.setId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(b));

        Brand result = brandService.findById(1L);

        assertThat(result).isEqualTo(b);
    }

    @Test
    void testFindById_notFound() {
        when(brandRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> brandService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Did not find Brand id");
    }

    @Test
    void testSave() {
        Brand b = new Brand("NewBrand");

        when(brandRepository.save(b)).thenReturn(b);

        Brand saved = brandService.save(b);

        assertThat(saved).isEqualTo(b);
    }

    @Test
    void testDeleteById() {
        brandService.deleteById(5L);
        verify(brandRepository, times(1)).deleteById(5L);
    }

    @Test
    void testFindByName() {
        Brand b = new Brand("Coca-Cola");

        when(brandRepository.findByName("Coca-Cola")).thenReturn(Optional.of(b));

        Optional<Brand> found = brandService.findByName("Coca-Cola");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Coca-Cola");
    }
}