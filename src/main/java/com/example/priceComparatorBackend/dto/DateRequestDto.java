package com.example.priceComparatorBackend.dto;

import java.time.LocalDate;

public class DateRequestDto {
    private LocalDate date;


    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}