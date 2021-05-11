package com.osvaldas.learning.parallel.service;

import com.osvaldas.learning.parallel.domain.Inventory;
import com.osvaldas.learning.parallel.domain.ProductOption;

import java.util.concurrent.CompletableFuture;

import static com.osvaldas.learning.parallel.util.CommonUtil.delay;

public class InventoryService {
    public Inventory addInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();
    }

    public CompletableFuture<Inventory> addInventory_CF(ProductOption productOption) {
        return CompletableFuture.supplyAsync(() -> {
            delay(500);
            return Inventory.builder()
                    .count(2).build();
        });
    }
}
