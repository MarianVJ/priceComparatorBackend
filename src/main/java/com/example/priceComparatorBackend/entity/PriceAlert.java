package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "price_alert")
public class PriceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Relație directă cu produsul (care include brand și category)
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @Column(name = "target_price", nullable = false)
    private double targetPrice;

    @Column(nullable = false)
    private String email;

    public PriceAlert() {}

    public PriceAlert(Product product, double targetPrice, String email) {
        this.product = product;
        this.targetPrice = targetPrice;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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