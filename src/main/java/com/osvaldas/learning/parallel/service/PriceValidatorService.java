package com.osvaldas.learning.parallel.service;


import com.osvaldas.learning.parallel.domain.checkout.CartItem;

import static com.osvaldas.learning.parallel.util.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        delay(500);
        return cartId == 7 || cartId == 9 || cartId == 11;
    }
}
