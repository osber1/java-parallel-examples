package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.domain.Product;
import com.osvaldas.learning.parallel.service.InventoryService;
import com.osvaldas.learning.parallel.service.ProductInfoService;
import com.osvaldas.learning.parallel.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {
    @Mock
    ProductInfoService productInfoService;

    @Mock
    ReviewService reviewService;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;

    @Test
    void testRetrieveProductDetailsWithInventoryAsync_whenReviewServiceError() {
        //given
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(anyString())).thenCallRealMethod();
        when(reviewService.retrieveReviews(anyString())).thenThrow(new RuntimeException("Exception Occurred"));
        when(inventoryService.addInventory(any())).thenCallRealMethod();

        //when
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryAsync(productId);

        //then
        assertThat(product).isNotNull();
        assertThat(product.getProductInfo().getProductOptions().size()).isGreaterThan(0);
        product.getProductInfo().getProductOptions()
                .forEach(p -> assertThat(p.getInventory()).isNotNull());
        assertThat(product.getReview()).isNotNull();
        assertThat(product.getReview().getNoOfReviews()).isEqualTo(0);
    }

    @Test
    void testRetrieveProductDetailsWithInventoryAsync_whenProductInfoServiceError() {
        //given
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(anyString())).thenThrow(new RuntimeException("Exception Occurred"));
        when(reviewService.retrieveReviews(anyString())).thenCallRealMethod();

        //then
        assertThatThrownBy(() -> productServiceUsingCompletableFuture.retrieveProductDetailsWithInventoryAsync(productId))
                .hasMessageContaining("Exception Occurred")
                .isInstanceOf(RuntimeException.class);
    }
}