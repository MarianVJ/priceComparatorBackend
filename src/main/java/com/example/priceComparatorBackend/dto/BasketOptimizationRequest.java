package com.example.priceComparatorBackend.dto;

import java.time.LocalDate;
import java.util.List;

public class BasketOptimizationRequest {
    private LocalDate date;
    private List<String> products;

    public BasketOptimizationRequest() {}

    public BasketOptimizationRequest(LocalDate date, List<String> products) {
        this.date = date;
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
