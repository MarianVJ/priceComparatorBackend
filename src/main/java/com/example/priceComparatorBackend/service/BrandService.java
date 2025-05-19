package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();

    Brand findById(long theId);

    Brand save(Brand theBrand);

    void deleteById(long theId);

    Optional<Brand> findByName(String name);
}
