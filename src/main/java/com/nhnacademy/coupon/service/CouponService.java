package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponJpaRepository couponJpaRepository;

    public List<CouponPolicy> getCouponPolicys(Pageable pageable) {
        return couponJpaRepository.findAll(pageable)
                .stream()
                .map(CouponPolicy::create)
                .toList();
    }
}
