package com.example.priceComparatorBackend.dao;

import com.example.priceComparatorBackend.entity.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
}