package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.StoreDiscountDateBatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreDiscountDateBatchProductRepository
        extends JpaRepository<StoreDiscountDateBatchProduct, Long> {

    @Query(value = """
                SELECT sddbp.store_discount_date_batch_product_id,
                       sddbp.store_discount_date_batch_id,
                       sddbp.product_id,
                       sddbp.percentage_of_discount
            FROM store_discount_date_batch_product sddbp
            JOIN store_discount_date_batch sddb on sddb.store_discount_date_batch_id = sddbp.store_discount_date_batch_id
            WHERE sddbp.product_id = :productId and sddb.store_id = :storeId
            """, nativeQuery = true)
    List<StoreDiscountDateBatchProduct> findByProductIdAndStoreId(@Param(
            "productId") Long categoryId, @Param(
            "storeId") Long storeId);
}

