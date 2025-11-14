package com.nhnacademy.coupon.service.maker.entity;

import com.nhnacademy.coupon.service.coupon.Coupon;

public interface CouponJpaEntityMaker {
    boolean match(Coupon coupon);
    void save(Coupon coupon);
}
