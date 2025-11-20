package com.nhnacademy.coupon.port.in.admin.policy;

import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;

record PolicyUpdateRequest(Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
     CouponPolicy createCouponPolicy(Long policyId) {
         return new  CouponPolicy(policyId, discountValue, minOrderPrice, maxDiscountPrice, couponDiscountType);
     }
}
