package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Product;
import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);

    @Query(value = """
        SELECT *
        FROM product p
        WHERE p.category_id = :categoryId
        """, nativeQuery = true)
    List<Product> findByProductCategory(@Param("categoryId") Long categoryId);
}
