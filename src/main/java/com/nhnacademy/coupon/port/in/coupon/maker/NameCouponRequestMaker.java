package com.nhnacademy.coupon.port.in.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.CouponType;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.port.in.coupon.CouponRequest;
import org.springframework.stereotype.Component;

@Component
class NameCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return request.type()== CouponType.NAME;
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new NameCoupon(id,request.name(),request.policyId(),request.quantity(),request.issueStartDate(),request.issueEndDate(),request.bookName());
    }
}
