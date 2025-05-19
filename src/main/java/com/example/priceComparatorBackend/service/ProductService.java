package com.example.priceComparatorBackend.service;


import com.example.priceComparatorBackend.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(long theId);

    Product save(Product theProduct);

    void deleteById(long theId);

    boolean existsById(Long id);
}
