package com.example.priceComparatorBackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;


@SqlResultSetMapping(
        name = "ProductDiscountPercentageDto",
        classes = @ConstructorResult(
                targetClass = ProductDiscountPercentageDto.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "discount", type = Double.class),
                        @ColumnResult(name = "price", type = Double.class),
                        @ColumnResult(name = "brand", type = String.class),
                        @ColumnResult(name = "store", type = String.class),
                        @ColumnResult(name = "currency", type = String.class)
                }
        )
)
public class ProductDiscountPercentageDto {
    private String name;
    private double discount;
    @JsonProperty("price_without_discount")
    private double price;
    private String brand;
    private String store;
    private final static String currency = "RON";
    public ProductDiscountPercentageDto() {

    }

    public ProductDiscountPercentageDto(String name, double discount,
                                        double price, String brand,
                                        String store) {
        this.name = name;
        this.discount = discount;
        this.price = price;
        this.store = store;
        this.brand = brand;
    }
    public String getCurrency() {
        return currency;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
