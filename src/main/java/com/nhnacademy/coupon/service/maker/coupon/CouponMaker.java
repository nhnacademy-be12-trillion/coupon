package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;


public interface CouponMaker {
    boolean match(CouponJpaEntity couponJpaEntity);
    Coupon make(CouponJpaEntity couponJpaEntity);
    boolean match(Coupon coupon);
    CouponJpaEntity make(Coupon coupon);
}
