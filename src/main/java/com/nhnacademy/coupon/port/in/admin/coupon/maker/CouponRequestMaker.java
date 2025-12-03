package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;

interface CouponRequestMaker {
    boolean match(CouponRequest request);
    Coupon make(Long id,CouponRequest request);
}
