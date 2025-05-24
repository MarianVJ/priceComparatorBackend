package com.example.priceComparatorBackend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductPriceDto {
    private String name;
    private double price;

    @JsonIgnore
    private String store; // can be null

    public ProductPriceDto() {}

    public ProductPriceDto(String name, double price, String store) {
        this.name = name;
        this.price = price;
        this.store = store;
    }

    public ProductPriceDto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
