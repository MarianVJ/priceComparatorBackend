package com.example.priceComparatorBackend.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDate;

public class ProductDatePriceDto {
    @JsonView({ProductPriceHistoryDto.Views.BrandView.class, ProductPriceHistoryDto.Views.CategoryView.class})
    private LocalDate date;
    @JsonView({ProductPriceHistoryDto.Views.BrandView.class, ProductPriceHistoryDto.Views.CategoryView.class})
    private double price;

    public ProductDatePriceDto() {
    }

    public ProductDatePriceDto(LocalDate date, double price) {
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
