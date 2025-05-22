package com.example.priceComparatorBackend.dto;

import java.util.List;
import java.util.Map;


public class BasketOptimizationResponse {

    // The Shop and the List of products of that shop
    private Map<String, List<ProductPriceDTO>> recommendedShopping;
    private Map<String, Double> storeTotals;
    private double basketTotal;

    // Observations or notes for the user, if the same product has same price
    // in different stores
    private List<String> notes;

    public BasketOptimizationResponse() {}

    public BasketOptimizationResponse(Map<String, List<ProductPriceDTO>> recommendedShopping,
                                      Map<String, Double> storeTotals,
                                      double basketTotal,
                                      List<String> notes) {
        this.recommendedShopping = recommendedShopping;
        this.storeTotals = storeTotals;
        this.basketTotal = basketTotal;
        this.notes = notes;
    }

    public Map<String, List<ProductPriceDTO>> getRecommendedShopping() {
        return recommendedShopping;
    }

    public void setRecommendedShopping(Map<String, List<ProductPriceDTO>> recommendedShopping) {
        this.recommendedShopping = recommendedShopping;
    }

    public Map<String, Double> getStoreTotals() {
        return storeTotals;
    }

    public void setStoreTotals(Map<String, Double> storeTotals) {
        this.storeTotals = storeTotals;
    }

    public double getBasketTotal() {
        return basketTotal;
    }

    public void setBasketTotal(double basketTotal) {
        this.basketTotal = basketTotal;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}


