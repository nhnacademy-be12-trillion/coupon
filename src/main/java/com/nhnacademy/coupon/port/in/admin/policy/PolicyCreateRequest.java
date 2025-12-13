package com.nhnacademy.coupon.port.in.admin.policy;

import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.service.CouponPolicyComposite;

record PolicyCreateRequest(String name,Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDiscountType couponDiscountType) {
    CouponPolicy createCouponPolicy() {
        return CouponPolicyComposite.couponPolicy(null, name,discountValue, minOrderPrice,maxDiscountPrice, couponDiscountType);
    }
}
