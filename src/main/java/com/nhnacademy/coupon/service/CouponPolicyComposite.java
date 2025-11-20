package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.policy.AllPricePolicy;
import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.domain.policy.NonePricePolicy;
import com.nhnacademy.coupon.domain.policy.OnlyMaxDisCountPricePolicy;
import com.nhnacademy.coupon.domain.policy.OnlyMinOrderPriceCouponPolicy;
import com.nhnacademy.coupon.error.CustomException;
import java.util.stream.Stream;

public class CouponPolicyComposite {
    private CouponPolicyComposite(){}
    public static CouponPolicy couponPolicy(Long id,Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType){
        return Stream.of(
                new AllPricePolicy(id,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                ,new OnlyMaxDisCountPricePolicy(id,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                ,new OnlyMinOrderPriceCouponPolicy(id,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                        ,new NonePricePolicy(id,minOrderPrice,maxDiscountPrice,discountValue,couponDiscountType)
                )
                .filter(CouponPolicy::match)
                .findAny()
                .orElseThrow(()->new CustomException("error.message.notFoundCouponPolicy"));
    }
}
