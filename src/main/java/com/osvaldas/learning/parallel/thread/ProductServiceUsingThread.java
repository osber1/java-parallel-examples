package com.osvaldas.learning.parallel.thread;

import com.osvaldas.learning.parallel.domain.Product;
import com.osvaldas.learning.parallel.domain.ProductInfo;
import com.osvaldas.learning.parallel.domain.Review;
import com.osvaldas.learning.parallel.service.ProductInfoService;
import com.osvaldas.learning.parallel.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static com.osvaldas.learning.parallel.util.CommonUtil.stopWatch;
import static com.osvaldas.learning.parallel.util.LoggerUtil.log;

@RequiredArgsConstructor
public class ProductServiceUsingThread {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    @SneakyThrows
    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);
    }

    private class ProductInfoRunnable implements Runnable {
        private ProductInfo productInfo;
        private final String productId;

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {
        private Review review;

        private final String productId;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}
