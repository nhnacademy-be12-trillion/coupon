package com.nhnacademy.coupon.port.in.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.CategoryCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.CouponType;
import com.nhnacademy.coupon.port.in.coupon.CouponRequest;
import org.springframework.stereotype.Component;

@Component
class CategoryCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return request.type()== CouponType.CATEGORY;
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new CategoryCoupon(id,request.name(),request.policyId(), request.quantity(),request.issueStartDate(),request.issueEndDate(),
                request.categoryName());
    }
}
