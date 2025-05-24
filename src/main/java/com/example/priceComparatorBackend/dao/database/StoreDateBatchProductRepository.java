package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface StoreDateBatchProductRepository extends JpaRepository<StoreDateBatchProduct, Long> {

    @Query(value = """
    SELECT 
        sdp.price * COALESCE(1 - sddbp.percentage_of_discount / 100, 1) AS effective_price
    FROM store_date_batch_product sdp
    JOIN store_date_batch sdb ON sdp.store_date_batch_id = sdb.store_date_batch_id
    LEFT JOIN store_discount_date_batch_product sddbp 
        ON sddbp.product_id = sdp.product_id
    LEFT JOIN store_discount_date_batch sddb
        ON sddb.store_discount_date_batch_id = sddbp.store_discount_date_batch_id
        AND sdb.store_id = sddb.store_id
        AND :date BETWEEN sddb.from_date AND sddb.to_date
    WHERE sdp.product_id = :productId
      AND sdb.batch_date <= :date
    ORDER BY sdb.batch_date DESC
    LIMIT 1
    """, nativeQuery = true)
    Double findEffectivePriceByProductIdAtDate(@Param("productId") Long productId, @Param("date") LocalDate date);
}