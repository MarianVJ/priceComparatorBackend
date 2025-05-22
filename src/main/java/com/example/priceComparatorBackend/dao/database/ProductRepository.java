package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);
}
