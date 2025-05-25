package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.dto.BestDealsProductDto;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BestBuysPerUnitRepositoryImpl
        implements BestBuysPerUnitRepository {

    private EntityManager entityManager;

    @Autowired
    public BestBuysPerUnitRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BestDealsProductDto> getBestBuysPerUnit(
            LocalDate theDate) {

        // This query is used to retrieve all products and compute the price per unit
        // for each product on a specific shopping date.
        // If a discount exists, the price will be reduced accordingly,
        // using the price from the latest available product batch at that time.

        String sql = """
                WITH discounted_prices AS (
                    SELECT
                        p.product_id,
                        p.name AS name,
                        b.name AS brand,
                        s.name AS store,
                        sdbp.price,
                        COALESCE(sddbp.percentage_of_discount, 0) AS discount_percentage,
                        p.package_quantity,
                        p.package_unit,
                        sdb.batch_date,
                        sdbp.currency,
                        ROUND(sdbp.price * (1 - COALESCE(sddbp.percentage_of_discount, 0) / 100), 2) AS final_price,
                        ROUND(
                            (sdbp.price * (1 - COALESCE(sddbp.percentage_of_discount, 0) / 100)) / NULLIF(p.package_quantity, 0),
                            4
                        ) AS value_per_unit
                    FROM product p
                    JOIN brand b ON b.brand_id = p.brand_id
                    JOIN store_date_batch_product sdbp ON sdbp.product_id = p.product_id
                    JOIN store_date_batch sdb ON sdb.store_date_batch_id = sdbp.store_date_batch_id
                    JOIN store s ON s.store_id = sdb.store_id
                    LEFT JOIN store_discount_date_batch sddb ON sddb.store_id = s.store_id
                        AND :theDate BETWEEN sddb.from_date AND sddb.to_date
                    LEFT JOIN store_discount_date_batch_product sddbp ON sddbp.store_discount_date_batch_id = sddb.store_discount_date_batch_id
                        AND sddbp.product_id = p.product_id
                    WHERE sdb.batch_date = (
                        SELECT MAX(sdb2.batch_date)
                        FROM store_date_batch sdb2
                        WHERE sdb2.store_id = s.store_id
                          AND sdb2.batch_date <= COALESCE(sddb.from_date, :theDate)
                    )
                )
                SELECT
                    dp.name,
                    dp.final_price AS price,
                    dp.brand,
                    dp.store,
                    dp.package_quantity AS packageQuantity,
                    dp.package_unit AS packageUnit,
                    dp.value_per_unit AS valuePerUnit
                FROM discounted_prices dp
                INNER JOIN (
                    SELECT product_id, MIN(value_per_unit) AS min_value
                    FROM discounted_prices
                    GROUP BY product_id
                ) AS best_prices ON best_prices.product_id = dp.product_id
                                 AND best_prices.min_value = dp.value_per_unit
                ORDER BY dp.value_per_unit ASC, dp.name
                """;

        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("theDate", theDate)
                .getResultList();

        List<BestDealsProductDto> results = rows.stream()
                .map(row -> {
                    String name = (String) row[0];
                    double price = ((Number) row[1]).doubleValue();
                    String brand = (String) row[2];
                    String store = (String) row[3];
                    double packageQuantity = ((Number) row[4]).doubleValue();
                    String packageUnit = (String) row[5];
                    double valuePerUnit = ((Number) row[6]).doubleValue();

                    // The convert of the unit of package
                    if ("ml".equalsIgnoreCase(packageUnit)) {
                        packageQuantity = packageQuantity / 1000.0; // ml -> l
                        packageUnit = "l";
                        valuePerUnit = price /
                                packageQuantity; // recompute price/unit
                    } else if ("g".equalsIgnoreCase(packageUnit)) {
                        packageQuantity = packageQuantity / 1000.0; // g -> kg
                        packageUnit = "kg";
                        valuePerUnit = price /
                                packageQuantity; // recompute price/unit
                    }

                    return new BestDealsProductDto(
                            name,
                            price,
                            brand,
                            store,
                            packageUnit,
                            packageQuantity,
                            valuePerUnit
                    );
                })
                .collect(Collectors.toList());

        // Sort the list in ascending order
        results.sort(Comparator.comparingDouble(BestDealsProductDto::getValuePerUnit));
        return results;
    }
}
