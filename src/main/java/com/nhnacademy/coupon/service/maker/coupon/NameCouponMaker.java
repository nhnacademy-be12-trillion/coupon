package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponKindColumn;
import com.nhnacademy.coupon.port.out.coupon.NameCouponJpaEntity;
import org.springframework.stereotype.Component;

@Component
class NameCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return couponJpaEntity.getCouponType()== CouponKindColumn.NAME;
    }

    @Override
    public Coupon make(CouponJpaEntity entity) {
        NameCouponJpaEntity couponJpaEntity = (NameCouponJpaEntity) entity;
        return new NameCoupon(couponJpaEntity.getId(), couponJpaEntity.getName(),couponJpaEntity.getPolicyId(),couponJpaEntity.getQuantity(),couponJpaEntity.getIssueStartDate(),couponJpaEntity.getIssueEndDate(),couponJpaEntity.getBookName());
    }
}
