package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;
import org.springframework.stereotype.Component;

@Component
class DefaultCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return true;
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new Coupon(id,request.name(),request.policyId(),request.quantity(),request.issueStartDate(),request.issueEndDate());
    }
}
