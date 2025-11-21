package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import org.springframework.stereotype.Component;

@Component
class NameCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return couponJpaEntity.getName()!=null;
    }

    @Override
    public Coupon make(CouponJpaEntity entity) {
        return new NameCoupon(entity.getId(), entity.getName(),entity.getPolicyId(),entity.getQuantity(),entity.getIssueStartDate(),entity.getIssueEndDate(),entity.getBookName());
    }

    @Override
    public boolean match(Coupon coupon) {
        return NameCoupon.class.isAssignableFrom(coupon.getClass());
    }

    @Override
    public CouponJpaEntity make(Coupon coupon) {
        return new CouponJpaEntity((NameCoupon)coupon);
    }
}
