package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Category findById(long theId);

    Category save(Category theCategory);

    void deleteById(long theId);

    Optional<Category> findByName(String name);
}
