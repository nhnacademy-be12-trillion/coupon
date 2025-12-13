package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.CategoryIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
class CategoryCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return request.categoryId()!=null;
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new CategoryIdCoupon(id,request.name(),request.policyId(), request.quantity(),request.issueStartDate(),request.issueEndDate(),
                request.categoryId());
    }
}
