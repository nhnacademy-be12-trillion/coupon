package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponKindColumn;
import com.nhnacademy.coupon.service.coupon.Coupon;
import org.springframework.stereotype.Component;

@Component
class DefaultCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return couponJpaEntity.getCouponType()== CouponKindColumn.DEFAULT;
    }

    @Override
    public Coupon make(CouponJpaEntity couponJpaEntity) {
        return new Coupon(couponJpaEntity.getId(), couponJpaEntity.getName(),couponJpaEntity.getPolicyId(),couponJpaEntity.getQuantity(),couponJpaEntity.getIssueStartDate(),couponJpaEntity.getIssueEndDate());
    }
}
