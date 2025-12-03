package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import java.util.function.ToLongBiFunction;

public enum CouponDisCountType {
    FIXED_AMOUNT((discount,price)->{
        if(discount<=0||discount!=Math.ceil(discount))
            throw new CustomException("error.message.notFixedAmount",new Object[]{discount});
        if(discount>price)
            throw new CustomException("error.message.notFixedAmount",new Object[]{discount});
        return discount.longValue();
    }),
    RATE((discount,price)->{
        if(0>=discount||discount>=1)
            throw new CustomException("error.message.notRate",new Object[]{discount});
        return (long)(price*discount);
    });
    private ToLongBiFunction<Double,Long> function;
    CouponDisCountType(ToLongBiFunction<Double,Long> function) {
        this.function = function;
    }
    public Price getSalePrice(Double discountValue,Price price) {
       return new Price(function.applyAsLong(discountValue, price.value()));
    }
}
