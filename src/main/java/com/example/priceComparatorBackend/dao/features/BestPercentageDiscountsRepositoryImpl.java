package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductDiscountPercentageDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BestPercentageDiscountsRepositoryImpl
        implements BestPercentageDiscountsRepository {


    private EntityManager entityManager;

    @Autowired
    public BestPercentageDiscountsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // By default this methods returns maximum 10 entries if available
    @Override
    public List<ProductDiscountPercentageDto> getBestPercentageDiscountsByDate(
            LocalDate todayDate) {

        // This query is used to retrieve the products with the highest discount percentage
        // on a specific shopping date.
        // The price returned will be the standard price, without the discount applied.
        String sql = """
                    WITH latest_discounts AS (
                        SELECT 
                            sddb.store_discount_date_batch_id,
                            sddb.store_id,
                            sddbp.product_id,
                            sddbp.percentage_of_discount,
                            ROW_NUMBER() OVER (
                                PARTITION BY sddb.store_id, sddbp.product_id 
                                ORDER BY sddb.from_date DESC
                            ) AS rn
                        FROM store_discount_date_batch sddb
                        JOIN store_discount_date_batch_product sddbp 
                            ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                        WHERE :todayDate BETWEEN sddb.from_date AND sddb.to_date
                    )
                
                    SELECT 
                        p.name AS name,
                        latest_discounts.percentage_of_discount AS discount,
                        sdbp.price AS price,
                        b.name AS brand,
                        s.name AS store
                    FROM latest_discounts
                    JOIN store s ON s.store_id = latest_discounts.store_id
                    JOIN product p ON p.product_id = latest_discounts.product_id
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                                              AND sdb.store_id = s.store_id
                    JOIN (
                        SELECT 
                            sdbp2.product_id, 
                            sdb2.store_id, 
                            MAX(
                                CASE 
                                    WHEN sdb2.batch_date <= :todayDate THEN sdb2.batch_date 
                                    ELSE NULL 
                                END
                            ) AS max_batch_date
                        FROM store_date_batch_product sdbp2
                        JOIN store_date_batch sdb2 
                            ON sdb2.store_date_batch_id = sdbp2.store_date_batch_id
                        GROUP BY sdbp2.product_id, sdb2.store_id
                    ) AS max_batch  ON max_batch.product_id = sdbp.product_id
                                    AND max_batch.store_id = sdb.store_id
                                    AND max_batch.max_batch_date = sdb.batch_date
                    WHERE latest_discounts.rn = 1
                    ORDER BY latest_discounts.percentage_of_discount DESC
                    LIMIT 10
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
