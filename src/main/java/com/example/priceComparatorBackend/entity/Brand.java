package com.example.priceComparatorBackend.entity;


import jakarta.persistence.*;


@Table(name="brand")
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // define Constructors
    public Brand(){

    }

    public Brand(String name) {
        this.name = name;
    }

    // define getter/setter
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

}