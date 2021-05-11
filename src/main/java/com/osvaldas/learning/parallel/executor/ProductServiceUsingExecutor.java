package com.osvaldas.learning.parallel.executor;

import com.osvaldas.learning.parallel.domain.Product;
import com.osvaldas.learning.parallel.domain.ProductInfo;
import com.osvaldas.learning.parallel.domain.Review;
import com.osvaldas.learning.parallel.service.ProductInfoService;
import com.osvaldas.learning.parallel.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.osvaldas.learning.parallel.util.CommonUtil.stopWatch;
import static com.osvaldas.learning.parallel.util.LoggerUtil.log;

@RequiredArgsConstructor
public class ProductServiceUsingExecutor {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    @SneakyThrows
    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));

        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
        executorService.shutdown();
    }
}
