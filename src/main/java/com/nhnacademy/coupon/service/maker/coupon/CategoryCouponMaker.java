package com.nhnacademy.coupon.service.maker.coupon;

import com.nhnacademy.coupon.port.out.coupon.CategoryCouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponKindColumn;
import com.nhnacademy.coupon.service.coupon.CategoryCoupon;
import com.nhnacademy.coupon.service.coupon.Coupon;
import org.springframework.stereotype.Component;

@Component
class CategoryCouponMaker implements CouponMaker {
    @Override
    public boolean match(CouponJpaEntity couponJpaEntity) {
        return couponJpaEntity.getCouponType()== CouponKindColumn.CATEGORY;
    }

    @Override
    public Coupon make(CouponJpaEntity entity) {
        CategoryCouponJpaEntity couponJpaEntity = (CategoryCouponJpaEntity) entity;
        return new CategoryCoupon(couponJpaEntity.getId(), couponJpaEntity.getName(),couponJpaEntity.getPolicyId(),couponJpaEntity.getQuantity(),couponJpaEntity.getIssueStartDate(),couponJpaEntity.getIssueEndDate(),couponJpaEntity.getCategoryName());
    }
}
