package com.nhnacademy.coupon.port.in.policy;

import com.nhnacademy.coupon.service.CouponDisCountType;
import com.nhnacademy.coupon.service.CouponPolicy;

record PolicyUpdateRequest(Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
     CouponPolicy createCouponPolicy(Long policyId) {
         return new  CouponPolicy(policyId, discountValue, minOrderPrice, maxDiscountPrice, couponDiscountType);
     }
}
