package com.nhnacademy.coupon.port.in.policy;

import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;

record PolicyCreateRequest(Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
    CouponPolicy createCouponPolicy() {
        return new  CouponPolicy(null, discountValue, minOrderPrice, maxDiscountPrice, couponDiscountType);
    }
}
