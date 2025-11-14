package com.nhnacademy.coupon.service.maker.entity;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.coupon.Coupon;
import com.nhnacademy.coupon.service.coupon.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultCouponJpaEntityMaker implements CouponJpaEntityMaker{
    private final CouponJpaRepository couponJpaRepository;

    @Override
    public boolean match(Coupon coupon) {
        return coupon.getType() == CouponType.DEFAULT;
    }

    @Override
    public void save(Coupon coupon) {
        couponJpaRepository.save(new CouponJpaEntity(coupon));
    }
}
