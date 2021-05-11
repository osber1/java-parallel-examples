package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.domain.*;
import com.osvaldas.learning.parallel.service.InventoryService;
import com.osvaldas.learning.parallel.service.ProductInfoService;
import com.osvaldas.learning.parallel.service.ReviewService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.osvaldas.learning.parallel.util.CommonUtil.stopWatch;
import static com.osvaldas.learning.parallel.util.LoggerUtil.log;

@RequiredArgsConstructor
public class ProductServiceUsingCompletableFuture {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> review = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product result = cfProductInfo.thenCombine(review, (a, b) -> new Product(productId, a, b)).join();
        stopWatch.stop();

        log("Total Time Taken : " + stopWatch.getTime());
        return result;
    }

    public CompletableFuture<Product> retrieveProductDetailsAsync(String productId) {
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> review = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        return cfProductInfo.thenCombine(review, (a, b) -> new Product(productId, a, b));
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(p -> {
                    p.setProductOptions(updateInventory(p));
                    return p;
                });

        CompletableFuture<Review> review = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product result = cfProductInfo.thenCombine(review, (a, b) -> new Product(productId, a, b)).join();
        stopWatch.stop();

        log("Total Time Taken : " + stopWatch.getTime());
        return result;
    }

    public Product retrieveProductDetailsWithInventoryAsync(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> cfProductInfo = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(p -> {
                    p.setProductOptions(updateInventoryAsync(p));
                    return p;
                });

        CompletableFuture<Review> review = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product result = cfProductInfo.thenCombine(review, (a, b) -> new Product(productId, a, b)).join();
        stopWatch.stop();

        log("Total Time Taken : " + stopWatch.getTime());
        return result;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions().stream()
                .peek(p -> {
                    Inventory inventory = inventoryService.addInventory(p);
                    p.setInventory(inventory);
                }).collect(Collectors.toList());
    }

    private List<ProductOption> updateInventoryAsync(ProductInfo productInfo) {
        return productInfo.getProductOptions().stream()
                .map(p -> CompletableFuture.supplyAsync(() -> inventoryService.addInventory(p))
                        .thenApply(i -> {
                            p.setInventory(i);
                            return p;
                        })).collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        InventoryService inventoryService = new InventoryService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();
    }
}
