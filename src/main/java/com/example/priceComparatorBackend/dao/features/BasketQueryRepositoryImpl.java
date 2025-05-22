package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.ProductPriceDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BasketQueryRepositoryImpl implements BasketQueryRepository {


    private EntityManager entityManager;

    @Autowired
    public BasketQueryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProductPriceDTO> findCheapestProductsByStore(
            List<String> productNames,
            LocalDate shoppingDate) {
        String sql = """
                SELECT product_name,
                       store_name,
                       final_price
                FROM (
                    SELECT p.name AS product_name,
                           s.name AS store_name,
                           ROUND(sdp.price * (1 - COALESCE(sdpd.percentage_of_discount, 0) / 100), 2) AS final_price,
                           ROW_NUMBER() OVER (PARTITION BY p.name ORDER BY ROUND(sdp.price * (1 - COALESCE(sdpd.percentage_of_discount, 0) / 100), 2) ASC) AS row_num
                    FROM store_date_batch_product sdp
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    JOIN product p ON p.product_id = sdp.product_id
                    LEFT JOIN store_discount_date_batch sddb ON sddb.store_id = s.store_id
                                                             AND sddb.from_date <= :date
                                                             AND sddb.to_date >= :date
                    LEFT JOIN store_discount_date_batch_product sdpd ON sdpd.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                                                                      AND sdpd.product_id = p.product_id
                    WHERE p.name IN (:names)
                      AND sdb.batch_date = (
                          SELECT MAX(sdb2.batch_date)
                          FROM store_date_batch sdb2
                          WHERE sdb2.store_id = s.store_id
                            AND sdb2.batch_date <= :date
                      )
                ) AS ranked_products
                WHERE row_num = 1
                ORDER BY product_name ASC               
                """;

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("names", productNames)
                .setParameter("date", shoppingDate)
                .getResultList();

        return results.stream()
                .map(row -> new ProductPriceDTO(
                        (String) row[0], // product_name
                        ((Number) row[2]).doubleValue(), // final_price
                        (String) row[1] // store_name
                ))
                .collect(Collectors.toList());
    }


}
