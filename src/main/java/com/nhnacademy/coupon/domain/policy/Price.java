package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;

public record Price(Long value) {
    public Price{
        if(value<0)
            throw new CustomException("error.message.priceIsPositive",new Object[]{value});
    }
}
