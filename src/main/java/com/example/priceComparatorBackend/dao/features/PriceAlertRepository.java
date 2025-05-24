package com.example.priceComparatorBackend.dao.features;

import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
}
