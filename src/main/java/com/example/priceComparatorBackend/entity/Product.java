package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;


@Entity
@Table(name="product")
public class Product {


    @Id
    @Column(name = "product_id")
    private Long id;


    private String name;
    private double packageQuantity;
    private String packageUnit;



    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    // define constructor
    public Product() {

    }

    public Product(Long id, Category category, Brand brand, String packageUnit, double packageQuantity, String name) {
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.packageUnit = packageUnit;
        this.packageQuantity = packageQuantity;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
