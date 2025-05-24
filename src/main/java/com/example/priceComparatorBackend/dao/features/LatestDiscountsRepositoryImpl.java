package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LatestDiscountsRepositoryImpl
        implements LatestDiscountsRepository {


    private EntityManager entityManager;

    @Autowired
    public LatestDiscountsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // By default this methods returns maximum 10 entries if available
    @Override
    public List<ProductDiscountPercentageDto> getLatestDiscounts(
            LocalDate todayDate) {

        String sql = """
    SELECT 
        p.name AS name,
        sddbp.percentage_of_discount AS discount,
        sdbp.price AS price,
        b.name AS brand,
        s.name AS store
    FROM store s
    JOIN store_discount_date_batch sddb 
        ON sddb.store_id = s.store_id
    JOIN store_discount_date_batch_product sddbp 
        ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
    JOIN product p 
        ON p.product_id = sddbp.product_id
    JOIN brand b
        ON b.brand_id = p.brand_id
    JOIN store_date_batch_product sdbp
        ON sdbp.product_id = p.product_id
    JOIN store_date_batch sdb
        ON sdb.store_date_batch_id = sdbp.store_date_batch_id
        AND sdb.store_id = s.store_id
    JOIN (
        SELECT 
            sdbp2.product_id, 
            sdb2.store_id, 
            MAX(
                CASE 
                    WHEN sdb2.batch_date <= sddb_inner.from_date 
                    THEN sdb2.batch_date 
                    ELSE NULL 
                END
            ) AS max_batch_date
        FROM store_discount_date_batch sddb_inner
        JOIN store_discount_date_batch_product sddbp2 
            ON sddbp2.store_discount_date_batch_id = sddb_inner.store_discount_date_batch_id
        JOIN store_date_batch_product sdbp2 
            ON sdbp2.product_id = sddbp2.product_id
        JOIN store_date_batch sdb2 
            ON sdb2.store_date_batch_id = sdbp2.store_date_batch_id
            AND sdb2.store_id = sddb_inner.store_id
        WHERE sddb_inner.from_date IN (:todayDate, DATE_SUB(:todayDate, INTERVAL 1 DAY))
          AND sddb_inner.to_date >= :todayDate
        GROUP BY sdbp2.product_id, sdb2.store_id
    ) AS max_batch 
        ON max_batch.product_id = sdbp.product_id
        AND max_batch.store_id = sdb.store_id
        AND max_batch.max_batch_date = sdb.batch_date
    WHERE sddb.from_date IN (:todayDate, DATE_SUB(:todayDate, INTERVAL 1 DAY))
      AND sddb.to_date >= :todayDate
""";
        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("todayDate", todayDate)
                .getResultList();

        return results.stream()
                .map(row -> new ProductDiscountPercentageDto(
                        (String) row[0],
                        ((Number) row[1]).doubleValue(),
                        ((Number) row[2]).doubleValue(),
                        (String) row[3],
                        (String) row[4]))
                .collect(Collectors.toList());
    }


}
