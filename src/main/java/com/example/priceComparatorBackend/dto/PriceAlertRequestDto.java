package com.example.priceComparatorBackend.dto;

public class PriceAlertRequestDto {

    private String productName;
    private String productBrand;
    private double targetPrice;
    private String email;

    public PriceAlertRequestDto() {

    }

    public PriceAlertRequestDto(String productName, String productBrand,
                                double targetPrice, String email) {
        this.productName = productName;
        this.productBrand = productBrand;
        this.targetPrice = targetPrice;
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
