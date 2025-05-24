package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StoreDateBatchProductRepository extends JpaRepository<StoreDateBatchProduct, Long> {

    @Query(value = """
        SELECT sdbp.store_date_batch_product_id, sdbp.store_date_batch_id, sdbp.product_id, sdbp.price, sdbp.currency
        FROM store_date_batch_product sdbp
        JOIN product p ON sdbp.product_id = p.product_id
        WHERE p.category_id = :categoryId
        """, nativeQuery = true)
    List<StoreDateBatchProduct> findByProductCategory(@Param("categoryId") Long categoryId);

    @Query(value = """
        SELECT MIN(sdb.batch_date)
        FROM store_date_batch_product sdbp
        JOIN store_date_batch sdb ON sdbp.store_date_batch_id = sdb.store_date_batch_id
        WHERE sdbp.product_id = :productId and sdb.batch_date > :batchDate
        """, nativeQuery = true)
    Optional<LocalDate> findNextBatchDate(@Param("batchDate")
                                                  LocalDate batchDate,
                                          @Param("productId") Long productId);
}