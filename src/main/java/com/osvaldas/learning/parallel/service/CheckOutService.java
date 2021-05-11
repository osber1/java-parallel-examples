package com.osvaldas.learning.parallel.service;

import com.osvaldas.learning.parallel.domain.checkout.Cart;
import com.osvaldas.learning.parallel.domain.checkout.CartItem;
import com.osvaldas.learning.parallel.domain.checkout.CheckoutResponse;
import com.osvaldas.learning.parallel.domain.checkout.CheckoutStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.osvaldas.learning.parallel.util.CommonUtil.startTimer;
import static com.osvaldas.learning.parallel.util.CommonUtil.timeTaken;

@Slf4j
@RequiredArgsConstructor
public class CheckOutService {
    private final PriceValidatorService priceValidatorService;

    public CheckoutResponse checkOut(Cart cart) {
        startTimer();
        List<CartItem> priceValidationList = cart.getCartItemList().parallelStream()
                .peek(i -> {
                    boolean isPriceInvalid = priceValidatorService.isCartItemInvalid(i);
                    i.setExpired(isPriceInvalid);
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        timeTaken();

        if (priceValidationList.size() > 0) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }

        double finalPrice = calculateFinalPrice_reduce(cart);
        log.info("Final Price is: {}", finalPrice);
        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    private double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(i -> i.getQuantity() * i.getRate())
                .mapToDouble(Double::doubleValue)
                .sum();
    }
    
    private double calculateFinalPrice_reduce(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(i -> i.getQuantity() * i.getRate())
                .reduce(0.0, Double::sum);
    }
}
