package com.example.priceComparatorBackend.service.features;


import com.example.priceComparatorBackend.dao.features.BasketQueryRepository;
import com.example.priceComparatorBackend.dto.BasketOptimizationRequest;
import com.example.priceComparatorBackend.dto.BasketOptimizationResponse;
import com.example.priceComparatorBackend.dto.ProductPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketQueryServiceImpl implements BasketQueryService {

    BasketQueryRepository basketQueryRepository;

    @Autowired
    public BasketQueryServiceImpl(BasketQueryRepository basketQueryRepository) {
        this.basketQueryRepository = basketQueryRepository;
    }


    public BasketOptimizationResponse findCheapestProductsByStore(
            BasketOptimizationRequest request) {

        List<String> notes = new ArrayList<>();
        BasketOptimizationResponse response;

        // If the date is missing , send back a response is missing
        if (request.getDate() == null) {
            notes.add("date is missing from the request");
            response = new BasketOptimizationResponse();
            response.setNotes(notes);
            return response;
        }

        // Search for the products with the lowest price across all markets
        // for the given date provided in the request.

        List<ProductPriceDto> pdList =
                basketQueryRepository.findCheapestProductsByStore(
                        request.getProducts(), request.getDate());

        Map<String, List<ProductPriceDto>> shopping = new HashMap<>();
        Map<String, Double> totals = new HashMap<>();
        double basketTotal = 0;

        String storeBuffer;
        for (ProductPriceDto p : pdList) {
            // Each p is added to the list for the actual store, but we do
            // not want to display the name of the store for each item
            storeBuffer = new String(p.getStore());

            // For each store add the product
            shopping.computeIfAbsent(storeBuffer, k -> new ArrayList<>())
                    .add(p);

            // For each store compute the actual amount of money spent there
            totals.put(p.getStore(),
                    totals.getOrDefault(p.getStore(), 0.0) + p.getPrice());
            basketTotal += p.getPrice();
        }


        // Verify if a product was not found in any market
        for (String product : request.getProducts()) {
            boolean found = pdList.stream()
                    .anyMatch(p -> p.getName().equals(product));

            if (!found) {
                notes.add("Product not found: " + product);
            }
        }


        response = new BasketOptimizationResponse(shopping, totals, basketTotal,
                notes);
        return response;
    }

}
