package com.osvaldas.learning.parallel.service;

import com.osvaldas.learning.parallel.domain.Product;
import com.osvaldas.learning.parallel.domain.ProductInfo;
import com.osvaldas.learning.parallel.domain.Review;
import lombok.RequiredArgsConstructor;

import static com.osvaldas.learning.parallel.util.CommonUtil.stopWatch;
import static com.osvaldas.learning.parallel.util.LoggerUtil.log;

@RequiredArgsConstructor
public class ProductService {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); // blocking call
        Review review = reviewService.retrieveReviews(productId); // blocking call

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductService productService = new ProductService(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }
}
