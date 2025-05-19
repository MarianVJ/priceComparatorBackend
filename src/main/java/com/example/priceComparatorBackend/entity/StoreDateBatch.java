package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "store_date_batch",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"store_id", "batch_date"})
        }
)
public class StoreDateBatch {

    // define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "batch_date")
    private LocalDate batchDate;

    // define constructors
    public StoreDateBatch() {

    }

    public StoreDateBatch(Store store, LocalDate batchDate) {
        this.store = store;
        this.batchDate = batchDate;
    }

    // define getter/setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(LocalDate batchDate) {
        this.batchDate = batchDate;
    }
}
