package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDateBatchProductRepository extends JpaRepository<StoreDateBatchProduct, Long> {
}