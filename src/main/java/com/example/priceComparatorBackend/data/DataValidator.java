package com.example.priceComparatorBackend.data;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;


@Service
public class DataValidator {

    private static final Logger logger = LoggerFactory.getLogger(DataValidator.class);

    public boolean isPositiveNumber(Double price) {
        // Verify if price is a valid number and greater than zero
        if (price == null || price <= 0) {
            logger.warn("Price is invalid: {}", price);
            return false;
        }
        return true;
    }

    public boolean isTextFieldValid(String name) {
        // Verify if  name is vid or null
        if (name == null || name.trim().isEmpty()) {
            logger.warn("name is invalid: {}", name);
            return false;
        }
        return true;
    }

    public boolean isDateValid(String dateString) {
        // Verify if  date is Valid
        try {
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeParseException e) {
            logger.warn("Date is invalid: {}", dateString);
            return false;
        }
    }
}