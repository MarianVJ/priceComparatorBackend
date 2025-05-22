package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "store_discount_date_batch_product")
public class StoreDiscountDateBatchProduct {

    // define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_discount_date_batch_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_discount_date_batch_id", nullable = false)
    private StoreDiscountDateBatch storeDiscountDateBatch;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "percentage_of_discount", nullable = false)
    private double percentageOfDiscount;

    // define constructors
    public StoreDiscountDateBatchProduct() {

    }

    public StoreDiscountDateBatchProduct(
            StoreDiscountDateBatch storeDiscountDateBatch, Product product,
            double percentageOfDiscount) {
        this.storeDiscountDateBatch = storeDiscountDateBatch;
        this.product = product;
        this.percentageOfDiscount = percentageOfDiscount;
    }

    // define getter/setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreDiscountDateBatch getStoreDiscountDateBatch() {
        return storeDiscountDateBatch;
    }

    public void setStoreDiscountDateBatch(
            StoreDiscountDateBatch storeDiscountDateBatch) {
        this.storeDiscountDateBatch = storeDiscountDateBatch;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public void setPercentageOfDiscount(double percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }
}
