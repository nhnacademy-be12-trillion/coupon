package com.nhnacademy.coupon.service.maker.entity;

import com.nhnacademy.coupon.domain.coupon.CategoryCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.CouponType;
import com.nhnacademy.coupon.port.out.coupon.CategoryCouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CategoryCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryCouponJpaEntityMaker implements CouponJpaEntityMaker{
    private final CategoryCouponJpaRepository categoryCouponJpaRepository;
    @Override
    public boolean match(Coupon coupon) {
        return coupon.getType()== CouponType.CATEGORY;
    }

    @Override
    public void save(Coupon domain) {
        CategoryCoupon coupon = (CategoryCoupon)domain;
        categoryCouponJpaRepository.save(new CategoryCouponJpaEntity(coupon));
    }
}
