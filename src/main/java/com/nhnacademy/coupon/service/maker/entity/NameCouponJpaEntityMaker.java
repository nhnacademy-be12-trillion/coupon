package com.nhnacademy.coupon.service.maker.entity;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.CouponType;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.port.out.coupon.NameCouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.NameCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NameCouponJpaEntityMaker implements CouponJpaEntityMaker{
    private final NameCouponJpaRepository nameCouponJpaRepository;
    @Override
    public boolean match(Coupon coupon) {
        return coupon.getType()== CouponType.NAME;
    }

    @Override
    public void save(Coupon domain) {
        NameCoupon coupon = (NameCoupon)domain;
        nameCouponJpaRepository.save(new NameCouponJpaEntity(coupon));
    }
}
