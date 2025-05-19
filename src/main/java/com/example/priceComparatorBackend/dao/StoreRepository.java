package com.example.priceComparatorBackend.dao;

import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}
