package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;

record Price(Long value) {
    Price{
        if(value<0)
            throw new CustomException("error.message.priceIsPositive",new Object[]{value});
    }
}
