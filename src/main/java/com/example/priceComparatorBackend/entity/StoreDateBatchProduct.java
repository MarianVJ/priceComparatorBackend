package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;

@Table(name="store_date_batch_product")
@Entity
public class StoreDateBatchProduct {
    @Id
    @GeneratedValue
    @Column(name = "store_date_batch_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_date_batch_id")
    private StoreDateBatch storeDateBatch;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double price;
    private String currency; // ex: RON

    public StoreDateBatchProduct() {

    }

    public StoreDateBatchProduct(StoreDateBatch storeDateBatch, Product product, double price, String currency) {
        this.storeDateBatch = storeDateBatch;
        this.product = product;
        this.price = price;
        this.currency = currency;
    }

    public StoreDateBatch getStore() {
        return storeDateBatch;
    }

    public void setStore(StoreDateBatch storeDateBatch) {
        this.storeDateBatch = storeDateBatch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}