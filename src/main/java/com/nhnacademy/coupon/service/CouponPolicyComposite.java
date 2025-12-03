package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.policy.AllPricePolicy;
import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.domain.policy.NonePricePolicy;
import com.nhnacademy.coupon.domain.policy.OnlyMaxDiscountPricePolicy;
import com.nhnacademy.coupon.domain.policy.OnlyMinOrderPriceCouponPolicy;
import com.nhnacademy.coupon.error.CustomException;
import java.util.stream.Stream;

public class CouponPolicyComposite {
    private CouponPolicyComposite(){}
    public static CouponPolicy couponPolicy(Long id,String name,Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDiscountType couponDiscountType){
        return Stream.of(
                new AllPricePolicy(id,name,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                ,new OnlyMaxDiscountPricePolicy(id,name,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                ,new OnlyMinOrderPriceCouponPolicy(id,name,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                        ,new NonePricePolicy(id,name,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                )
                .filter(CouponPolicy::match)
                .findFirst()
                .orElseThrow(()->new CustomException("error.message.notFoundCouponPolicy"));
    }
}
