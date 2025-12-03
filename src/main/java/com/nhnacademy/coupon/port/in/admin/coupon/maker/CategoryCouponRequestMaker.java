package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.CategoryCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
class CategoryCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return request.categoryName()!=null&&!request.categoryName().isBlank();
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new CategoryCoupon(id,request.name(),request.policyId(), request.quantity(),request.issueStartDate(),request.issueEndDate(),
                request.categoryName());
    }
}
