package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;

public record CouponPolicy(Long id, Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
    public CouponPolicy{
        if(discountValue<0){
            throw new CustomException("error.message.discountValueIsNegative",new  Object[]{String.valueOf(discountValue)});
        }
        if(discountValue>maxDiscountPrice){
            throw new CustomException("error.message.maxDiscountPrice",new  Object[]{String.valueOf(maxDiscountPrice),String.valueOf(discountValue)});
        }

        if(couponDiscountType==CouponDisCountType.FIXED_AMOUNT
                && discountValue!=Math.floor(discountValue.doubleValue())){
            throw new CustomException("error.message.fixAmount",new   Object[]{String.valueOf(discountValue),String.valueOf(discountValue)});
        }

        if(couponDiscountType==CouponDisCountType.RATE
        && discountValue>100){
            throw new CustomException("error.message.rate",new  Object[]{String.valueOf(discountValue),String.valueOf(discountValue)});
        }
    }
}