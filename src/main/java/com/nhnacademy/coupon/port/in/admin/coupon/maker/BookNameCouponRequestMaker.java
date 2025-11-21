package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
class BookNameCouponRequestMaker implements CouponRequestMaker {
    @Override
    public boolean match(CouponRequest request) {
        return request.bookName()!=null&&!request.bookName().isBlank();
    }

    @Override
    public Coupon make(Long id,CouponRequest request) {
        return new NameCoupon(id,request.name(),request.policyId(),request.quantity(),request.issueStartDate(),request.issueEndDate(),request.bookName());
    }
}
