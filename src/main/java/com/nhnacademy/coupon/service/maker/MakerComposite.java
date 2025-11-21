package com.nhnacademy.coupon.service.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.service.maker.coupon.CouponMaker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MakerComposite {
    private final List<CouponMaker> couponMakers;

    public Coupon makeCoupon(CouponJpaEntity entity){
        return couponMakers.stream()
                .filter(couponMaker -> couponMaker.match(entity))
                .findAny()
                .orElseThrow(()->new CustomException("error.message.notFoundCouponMaker"))
                .make(entity);
    }

    public CouponJpaEntity makeCouponEntity(Coupon coupon) {
        return couponMakers.stream()
                .filter(couponMaker -> couponMaker.match(coupon))
                .findAny()
                .orElseThrow(()->new CustomException("error.message.notFoundCouponMaker"))
                .make(coupon);
    }
}
