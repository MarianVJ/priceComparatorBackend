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

    @Query(value = """
    SELECT  p.product_id, p.name, p.package_quantity, p.package_unit,
                              p.brand_id, p.category_id
    FROM product p
    JOIN brand b ON b.brand_id = p.brand_id
    WHERE p.name = :name AND b.name = :brand
    """, nativeQuery = true)
    Product findByNameAndBrand(@Param("name") String name,
                                     @Param("brand") String brand);
}
