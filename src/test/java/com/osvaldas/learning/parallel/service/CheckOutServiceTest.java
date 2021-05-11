package com.osvaldas.learning.parallel.service;

import com.osvaldas.learning.parallel.domain.checkout.Cart;
import com.osvaldas.learning.parallel.domain.checkout.CartItem;
import com.osvaldas.learning.parallel.domain.checkout.CheckoutResponse;
import com.osvaldas.learning.parallel.domain.checkout.CheckoutStatus;
import com.osvaldas.learning.parallel.util.DataSet;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckOutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckOutService checkOutService = new CheckOutService(priceValidatorService);

    @Test
    void testCheckOut_when6Items() {
        //given
        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkOut(cart);

        //then
        assertThat(checkoutResponse.getCheckoutStatus()).isEqualTo(CheckoutStatus.SUCCESS);
        assertThat(checkoutResponse.getFinalRate() > 0).isTrue();
    }

    @Test
    public void testCheckOut() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<CartItem>());
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
    }

    @Test
    public void testCheckOut2() {
        CheckOutService checkOutService = new CheckOutService(null);

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<CartItem>());
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
    }

    @Test
    public void testCheckOut3() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(0);
        cartItem.setItemId(42);

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(cartItem);

        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
        assertEquals("Cart(cardId=null, cartItemList=[CartItem(itemId=42, itemName=null, rate=0.0, quantity=0,"
                + " isExpired=false)])", cart.toString());
    }

    @Test
    public void testCheckOut4() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(0);
        cartItem.setItemId(7);

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(cartItem);

        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.FAILURE, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=FAILURE, errorList=[CartItem(itemId=7, itemName=null, rate=0.0,"
                + " quantity=0, isExpired=true)], finalRate=0.0)", actualCheckOutResult.toString());
        assertEquals("Cart(cardId=null, cartItemList=[CartItem(itemId=7, itemName=null, rate=0.0, quantity=0,"
                + " isExpired=true)])", cart.toString());
    }

    @Test
    public void testCheckOut5() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<CartItem>());
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
    }

    @Test
    public void testCheckOut6() {
        CheckOutService checkOutService = new CheckOutService(null);

        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<CartItem>());
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
    }

    @Test
    public void testCheckOut7() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());
        cartItemList.add(new CartItem());

        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        checkOutService.checkOut(cart);
    }

    @Test
    public void testCheckOut8() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(0);
        cartItem.setItemId(42);

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(cartItem);

        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.SUCCESS, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=SUCCESS, errorList=[], finalRate=0.0)",
                actualCheckOutResult.toString());
        assertEquals(0.0, actualCheckOutResult.getFinalRate());
        assertEquals("Cart(cardId=null, cartItemList=[CartItem(itemId=42, itemName=null, rate=0.0, quantity=0,"
                + " isExpired=false)])", cart.toString());
    }

    @Test
    public void testCheckOut9() {
        CheckOutService checkOutService = new CheckOutService(new PriceValidatorService());

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(0);
        cartItem.setItemId(7);

        ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(cartItem);

        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        CheckoutResponse actualCheckOutResult = checkOutService.checkOut(cart);
        assertEquals(CheckoutStatus.FAILURE, actualCheckOutResult.getCheckoutStatus());
        assertEquals("CheckoutResponse(checkoutStatus=FAILURE, errorList=[CartItem(itemId=7, itemName=null, rate=0.0,"
                + " quantity=0, isExpired=true)], finalRate=0.0)", actualCheckOutResult.toString());
        assertEquals("Cart(cardId=null, cartItemList=[CartItem(itemId=7, itemName=null, rate=0.0, quantity=0,"
                + " isExpired=true)])", cart.toString());
    }

    @Test
    void testCheckOut_when13Items() {
        //given
        Cart cart = DataSet.createCart(13);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkOut(cart);

        //then
        assertThat(checkoutResponse.getCheckoutStatus()).isEqualTo(CheckoutStatus.FAILURE);
    }
}