package com.example.priceComparatorBackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "BestDealsProductDto",
        classes = @ConstructorResult(
                targetClass = com.example.priceComparatorBackend.dto.BestDealsProductDto.class,
                columns = {
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "price", type = Double.class),
                        @ColumnResult(name = "brand", type = String.class),
                        @ColumnResult(name = "store", type = String.class),
                        @ColumnResult(name = "currency", type =
                                String.class),
                        @ColumnResult(name = "packageQuantity", type =
                                Double.class),
                        @ColumnResult(name = "packageUnit", type =
                                String.class),
                        @ColumnResult(name = "valuePerUnit", type = Double.class)
                }
        )
)
public class BestDealsProductDto {
    private final static String currency = "RON";
    private String name;
    @JsonProperty("final_price")
    private double price;
    private String brand;
    private String store;
    private double packageQuantity;
    private String packageUnit;
    private double valuePerUnit;

    public BestDealsProductDto() {

    }

    public BestDealsProductDto(String name, double price,
                               String brand, String store,
                               String packageUnit,
                               Double packageQuantity,
                               Double valuePerUnit) {
        this.name = name;
        this.price = price;
        this.store = store;
        this.brand = brand;
        this.packageQuantity = packageQuantity;
        this.packageUnit = packageUnit;
        this.valuePerUnit = valuePerUnit;
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

    public double getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(double packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageUnit() {
        return packageUnit;
    }

    public void setPackageUnit(String packageUnit) {
        this.packageUnit = packageUnit;
    }

    public double getValuePerUnit() {
        return valuePerUnit;
    }

    public void setValuePerUnit(double valuePerUnit) {
        this.valuePerUnit = valuePerUnit;
    }
}

