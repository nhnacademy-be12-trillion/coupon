package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order
@Component
class DefaultCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return true;
    }

    @Override
    public Coupon make(CouponJpaEntity couponJpaEntity) {
        return new Coupon(couponJpaEntity.getId(), couponJpaEntity.getName(),couponJpaEntity.getPolicyId(),couponJpaEntity.getQuantity(),couponJpaEntity.getIssueStartDate(),couponJpaEntity.getIssueEndDate());
    }

    @Override
    public boolean match(Coupon coupon) {
        return true;
    }

    @Override
    public CouponJpaEntity make(Coupon coupon) {
        return new CouponJpaEntity(coupon);
    }
}
