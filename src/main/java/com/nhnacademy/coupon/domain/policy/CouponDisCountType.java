package com.nhnacademy.coupon.domain.policy;

import java.util.function.ToLongBiFunction;

public enum CouponDisCountType {
    FIXED_AMOUNT((discount,price)-> discount.longValue()),
    RATE((discount,price)->(long)(price*discount));
    private ToLongBiFunction<Double,Long> function;
    CouponDisCountType(ToLongBiFunction<Double,Long> function) {
        this.function = function;
    }
    public Price getSalePrice(Double discountValue,Price price) {
       return new Price(function.applyAsLong(discountValue, price.value()));
    }
}
