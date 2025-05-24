package com.example.priceComparatorBackend.dto;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class ProductPriceHistoryDto {

    @JsonView({Views.BrandView.class, Views.CategoryView.class})
    private String productName;


    @JsonView(Views.CategoryView.class)
    private String productBrand;


    @JsonView(Views.BrandView.class)
    private String productCategory;

    @JsonView({Views.BrandView.class, Views.CategoryView.class})
    private String store;
    @JsonView({Views.BrandView.class, Views.CategoryView.class})
    private List<ProductDatePriceDto> dataPoints;

    public ProductPriceHistoryDto() {
    }

    public ProductPriceHistoryDto(String productName, String productBrand,
                                  String productCategory,
                                  String store,
                                  List<ProductDatePriceDto> dataPoints) {
        this.productName = productName;
        this.store = store;
        this.dataPoints = dataPoints;
        this.productBrand = productBrand;
        this.productCategory = productCategory;
    }

    // class used for selecting when to include a field with different endpoints
    // when the history is queried by category we do not include the category
    // field (and for the brand the same behaviour_
    public static class Views {
        public static class BrandView {}
        public static class CategoryView {}
    }

    public String getProductName() {
        return productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public List<ProductDatePriceDto> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<ProductDatePriceDto> dataPoints) {
        this.dataPoints = dataPoints;
    }
}