package com.nhnacademy.coupon.service.maker;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;


interface CouponMaker {
    boolean match(CouponJpaEntity couponJpaEntity);
    Coupon make(CouponJpaEntity couponJpaEntity);
}
