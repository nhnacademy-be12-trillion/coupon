package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.Coupon;
import com.nhnacademy.coupon.service.maker.CouponMakerComposite;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponJpaRepository couponJpaRepository;
    private final CouponMakerComposite couponMakerComposite;

    public Collection<Coupon> findAll(Pageable pageable) {
        return couponJpaRepository.findAll(pageable)
                .stream()
                .map(couponMakerComposite::makeCoupon)
                .toList();
    }
}
