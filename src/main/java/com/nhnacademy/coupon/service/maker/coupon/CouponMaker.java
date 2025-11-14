package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.service.coupon.Coupon;


public interface CouponMaker {
    boolean match(CouponJpaEntity couponJpaEntity);
    Coupon make(CouponJpaEntity couponJpaEntity);
}
