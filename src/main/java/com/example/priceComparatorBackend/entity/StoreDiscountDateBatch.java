package com.example.priceComparatorBackend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(
        name = "store_discount_date_batch",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"store_id", "from_date", "to_date"})
        }
)
public class StoreDiscountDateBatch {

    // define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_discount_date_batch_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;


    // define constructors
    public StoreDiscountDateBatch() {

    }


    public StoreDiscountDateBatch(Store store, LocalDate fromDate,
                                  LocalDate toDate) {
        this.store = store;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    // method for validating that fromDate is earlier than toDate
    public boolean isValidDateRange() {
        if (fromDate == null || toDate == null) {
            return true;
        }
        return fromDate.isBefore(toDate);
    }
}
