package com.nhnacademy.coupon.port.in.admin.policy;

import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.service.CouponPolicyComposite;

record PolicyCreateRequest(Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
    CouponPolicy createCouponPolicy() {
        return CouponPolicyComposite.couponPolicy(null, discountValue, minOrderPrice,maxDiscountPrice, couponDiscountType);
    }
}
