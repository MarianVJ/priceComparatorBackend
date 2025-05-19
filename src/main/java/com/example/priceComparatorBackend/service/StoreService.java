package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    List<Store> findAll();

    Store findById(long theId);

    Store save(Store theStore);

    void deleteById(long theId);

    Optional<Store> findByName(String name);
}
