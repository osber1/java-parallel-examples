package com.osvaldas.learning.parallel.completable.future;

import com.osvaldas.learning.parallel.domain.Product;
import com.osvaldas.learning.parallel.service.InventoryService;
import com.osvaldas.learning.parallel.service.ProductInfoService;
import com.osvaldas.learning.parallel.service.ReviewService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProductServiceUsingCompletableFutureTest {

    private final ProductInfoService pis = new ProductInfoService();
    private final ReviewService rs = new ReviewService();
    private final InventoryService is = new InventoryService();
    ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(pis, rs, is);

    @Test
    void testRetrieveProductDetails() {
        //given
        String productId = "ABC123";

        //when
        Product product = pscf.retrieveProductDetails(productId);

        //then
        assertThat(product).isNotNull();
        assertThat(product.getProductInfo().getProductOptions().size()).isGreaterThan(0);
        assertThat(product.getReview()).isNotNull();
    }

    @Test
    void testRetrieveProductDetailsWithInventory() {
        //given
        String productId = "ABC123";

        //when
        Product product = pscf.retrieveProductDetailsWithInventory(productId);

        //then
        assertThat(product).isNotNull();
        assertThat(product.getProductInfo().getProductOptions().size()).isGreaterThan(0);
        product.getProductInfo().getProductOptions()
                .forEach(p -> assertThat(p.getInventory()).isNotNull());
        assertThat(product.getReview()).isNotNull();
    }

    @Test
    void testRetrieveProductDetailsWithInventoryAsync() {
        //given
        String productId = "ABC123";

        //when
        Product product = pscf.retrieveProductDetailsWithInventoryAsync(productId);

        //then
        assertThat(product).isNotNull();
        assertThat(product.getProductInfo().getProductOptions().size()).isGreaterThan(0);
        product.getProductInfo().getProductOptions()
                .forEach(p -> assertThat(p.getInventory()).isNotNull());
        assertThat(product.getReview()).isNotNull();
    }
}