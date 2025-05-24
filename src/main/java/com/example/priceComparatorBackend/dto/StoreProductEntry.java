package com.example.priceComparatorBackend.dto;

public class StoreProductEntry {
    private Long storeId;
    private Long storeDateBatchProductId;

    public StoreProductEntry(Long storeId, Long storeDateBatchProductId) {
        this.storeId = storeId;
        this.storeDateBatchProductId = storeDateBatchProductId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public Long getStoreDateBatchProductId() {
        return storeDateBatchProductId;
    }
}