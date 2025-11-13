package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;

public record CouponPolicy(Long id, Double discountValue, Long minOrderPrice, Long maxDiscountPrice, CouponDisCountType couponDiscountType) {
    static CouponPolicy create(CouponPolicyJpaEntity entity) {
        return new CouponPolicy(entity.getId(), entity.getDiscountValue(), entity.getMinOrderPrice(), entity.getMaxDiscountPrice(),CouponDisCountType.getCouponDisCountType(entity.getDiscountType()));
    }
}