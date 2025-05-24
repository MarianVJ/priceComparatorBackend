package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dao.database.*;
import com.example.priceComparatorBackend.dto.ProductDatePriceDto;
import com.example.priceComparatorBackend.dto.ProductPriceHistoryDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DynamicPriceHistoryRepositoryImpl
        implements DynamicPriceHistoryRepository {

    private final EntityManager entityManager;




    @Autowired
    public DynamicPriceHistoryRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByCategory(
            String category) {
        String sql = """
                WITH discounted_dates AS (
                    SELECT 
                        sddb.store_id,
                        sddbp.product_id,
                        sddb.from_date,
                        sddb.to_date,
                        sddbp.percentage_of_discount
                    FROM store_discount_date_batch sddb
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                ),
                
                base_prices AS (
                    -- Prețuri fără discount (batch_date), excluzând batch_date-uri care coincid cu un from_date de discount
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        b.name AS brand_name,
                        s.name AS store_name,
                        sdb.batch_date AS date,
                        sdbp.price AS base_price,
                        NULL AS discount_percentage,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    LEFT JOIN discounted_dates dd ON dd.store_id = s.store_id 
                                                AND dd.product_id = p.product_id
                                                AND dd.from_date = sdb.batch_date
                    WHERE c.name = :category
                      AND dd.from_date IS NULL
                
                    UNION ALL
                
                    -- Prețuri cu discount (from_date)
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        b.name AS brand_name,
                        s.name AS store_name,
                        sddb.from_date AS date,
                        (
                            SELECT sdbp.price
                            FROM store_date_batch_product sdbp
                            JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                            WHERE sdb.store_id = s.store_id
                              AND sdbp.product_id = p.product_id
                              AND sdb.batch_date <= sddb.from_date
                            ORDER BY sdb.batch_date DESC
                            LIMIT 1
                        ) AS base_price,
                        sddbp.percentage_of_discount,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_discount_date_batch sddb ON sddb.store_id IS NOT NULL
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                    JOIN store s ON s.store_id = sddb.store_id
                    WHERE c.name = :category
                      AND sddbp.product_id = p.product_id
                      AND EXISTS (
                        SELECT 1
                        FROM store_date_batch_product sdbp
                        JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                        WHERE sdb.store_id = s.store_id
                          AND sdbp.product_id = p.product_id
                          AND sdb.batch_date <= sddb.from_date
                      )
                
                    UNION ALL
                
                    -- Revenire la prețul normal după expirarea discount-ului (to_date + 1 zi)
                    SELECT
                        p.product_id,
                        p.name AS product_name,
                        b.name AS brand_name,
                        s.name AS store_name,
                        DATE_ADD(sddb.to_date, INTERVAL 1 DAY) AS date,
                        (
                            SELECT sdbp.price
                            FROM store_date_batch_product sdbp
                            JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                            WHERE sdb.store_id = s.store_id
                              AND sdbp.product_id = p.product_id
                              AND sdb.batch_date <= DATE_ADD(sddb.to_date, INTERVAL 1 DAY)
                            ORDER BY sdb.batch_date DESC
                            LIMIT 1
                        ) AS base_price,
                        NULL AS discount_percentage,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_discount_date_batch sddb ON sddb.store_id IS NOT NULL
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                    JOIN store s ON s.store_id = sddb.store_id
                    WHERE c.name = :category
                      AND sddbp.product_id = p.product_id
                ),
                
                discounted_batch_prices AS (
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        b.name AS brand_name,
                        s.name AS store_name,
                        sdb.batch_date AS date,
                        sdbp.price AS base_price,
                        dd.percentage_of_discount,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    JOIN discounted_dates dd ON dd.store_id = s.store_id AND dd.product_id = p.product_id
                    WHERE c.name = :category
                      AND sdb.batch_date > dd.from_date
                      AND sdb.batch_date <= dd.to_date
                ),
                
                all_prices AS (
                    SELECT * FROM base_prices
                    UNION ALL
                    SELECT * FROM discounted_batch_prices
                ),
                
                ranked_prices AS (
                    SELECT 
                        ap.*,
                        ROW_NUMBER() OVER (
                            PARTITION BY product_id, store_id, date 
                            ORDER BY CASE WHEN discount_percentage IS NOT NULL THEN 1 ELSE 2 END
                        ) AS rn
                    FROM all_prices ap
                )
                
                SELECT 
                    product_name,
                    brand_name,
                    store_name,
                    date,
                    ROUND(
                        base_price * (1 - COALESCE(discount_percentage, 0) / 100),
                        2
                    ) AS final_price
                FROM ranked_prices
                WHERE rn = 1
                ORDER BY product_name, store_name, date;
                """;
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("category", category)
                .getResultList();

        Map<String, ProductPriceHistoryDto> grouped = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String productName = (String) row[0];
            String brand = (String) row[1];
            String store = (String) row[2];
            java.sql.Date sqlDate = (java.sql.Date) row[3];
            LocalDate date = sqlDate.toLocalDate();
            double price = ((Number) row[4]).doubleValue();

            String key = productName + "|" + brand + "|" + store;
            grouped.computeIfAbsent(key, k ->
                    new ProductPriceHistoryDto(productName, brand,null, store,
                            new ArrayList<>())
            ).getDataPoints().add(new ProductDatePriceDto(date, price));
        }

        return new ArrayList<>(grouped.values());
    }


    @Override
    public List<ProductPriceHistoryDto> getDynamicPriceHistoryRepositoryByBrand(
            String brand) {
        String sql = """
                WITH discounted_dates AS (
                    SELECT 
                        sddb.store_id,
                        sddbp.product_id,
                        sddb.from_date,
                        sddb.to_date,
                        sddbp.percentage_of_discount
                    FROM store_discount_date_batch sddb
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                ),
                
                base_prices AS (
                    -- Prețuri fără discount (batch_date), excluzând batch_date-uri care coincid cu un from_date de discount
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        c.name AS category_name,
                        s.name AS store_name,
                        sdb.batch_date AS date,
                        sdbp.price AS base_price,
                        NULL AS discount_percentage,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    LEFT JOIN discounted_dates dd ON dd.store_id = s.store_id 
                                                AND dd.product_id = p.product_id
                                                AND dd.from_date = sdb.batch_date
                    WHERE b.name = :brand
                      AND dd.from_date IS NULL
                
                    UNION ALL
                
                    -- Prețuri cu discount (from_date)
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        c.name AS category,
                        s.name AS store_name,
                        sddb.from_date AS date,
                        (
                            SELECT sdbp.price
                            FROM store_date_batch_product sdbp
                            JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                            WHERE sdb.store_id = s.store_id
                              AND sdbp.product_id = p.product_id
                              AND sdb.batch_date <= sddb.from_date
                            ORDER BY sdb.batch_date DESC
                            LIMIT 1
                        ) AS base_price,
                        sddbp.percentage_of_discount,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_discount_date_batch sddb ON sddb.store_id IS NOT NULL
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                    JOIN store s ON s.store_id = sddb.store_id
                    WHERE b.name = :brand
                      AND sddbp.product_id = p.product_id
                      AND EXISTS (
                        SELECT 1
                        FROM store_date_batch_product sdbp
                        JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                        WHERE sdb.store_id = s.store_id
                          AND sdbp.product_id = p.product_id
                          AND sdb.batch_date <= sddb.from_date
                      )
                
                    UNION ALL
                
                    -- Revenire la prețul normal după expirarea discount-ului (to_date + 1 zi)
                    SELECT
                        p.product_id,
                        p.name AS product_name,
                        c.name AS category_name,
                        s.name AS store_name,
                        DATE_ADD(sddb.to_date, INTERVAL 1 DAY) AS date,
                        (
                            SELECT sdbp.price
                            FROM store_date_batch_product sdbp
                            JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                            WHERE sdb.store_id = s.store_id
                              AND sdbp.product_id = p.product_id
                              AND sdb.batch_date <= DATE_ADD(sddb.to_date, INTERVAL 1 DAY)
                            ORDER BY sdb.batch_date DESC
                            LIMIT 1
                        ) AS base_price,
                        NULL AS discount_percentage,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_discount_date_batch sddb ON sddb.store_id IS NOT NULL
                    JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                    JOIN store s ON s.store_id = sddb.store_id
                    WHERE b.name = :brand
                      AND sddbp.product_id = p.product_id
                ),
                
                discounted_batch_prices AS (
                    SELECT 
                        p.product_id,
                        p.name AS product_name,
                        c.name AS category_name,
                        s.name AS store_name,
                        sdb.batch_date AS date,
                        sdbp.price AS base_price,
                        dd.percentage_of_discount,
                        s.store_id
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN category c ON c.category_id = p.category_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    JOIN discounted_dates dd ON dd.store_id = s.store_id AND dd.product_id = p.product_id
                    WHERE b.name = :brand
                      AND sdb.batch_date > dd.from_date
                      AND sdb.batch_date <= dd.to_date
                ),
                
                all_prices AS (
                    SELECT * FROM base_prices
                    UNION ALL
                    SELECT * FROM discounted_batch_prices
                ),
                
                ranked_prices AS (
                    SELECT 
                        ap.*,
                        ROW_NUMBER() OVER (
                            PARTITION BY product_id, store_id, date 
                            ORDER BY CASE WHEN discount_percentage IS NOT NULL THEN 1 ELSE 2 END
                        ) AS rn
                    FROM all_prices ap
                )
                
                SELECT 
                    product_name,
                    category_name,
                    store_name,
                    date,
                    ROUND(
                        base_price * (1 - COALESCE(discount_percentage, 0) / 100),
                        2
                    ) AS final_price
                FROM ranked_prices
                WHERE rn = 1
                ORDER BY product_name, store_name, date;
                """;
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("brand", brand)
                .getResultList();

        Map<String, ProductPriceHistoryDto> grouped = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String productName = (String) row[0];
            String category = (String) row[1];
            String store = (String) row[2];
            java.sql.Date sqlDate = (java.sql.Date) row[3];
            LocalDate date = sqlDate.toLocalDate();
            double price = ((Number) row[4]).doubleValue();

            String key = productName + "|" + brand + "|" + store;
            grouped.computeIfAbsent(key, k ->
                    new ProductPriceHistoryDto(productName, null,category,
                            store,
                            new ArrayList<>())
            ).getDataPoints().add(new ProductDatePriceDto(date, price));
        }

        return new ArrayList<>(grouped.values());
    }

}