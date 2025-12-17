package com.nhnacademy.coupon.domain;

import com.nhnacademy.coupon.domain.policy.Price;

public record Book(Long price, Long bookId, Long categoryId) {
    public Price getPrice(){
        return new Price(price);
    }
}
