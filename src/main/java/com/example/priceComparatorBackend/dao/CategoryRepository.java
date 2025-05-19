package com.example.priceComparatorBackend.dao;

import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);;
}
