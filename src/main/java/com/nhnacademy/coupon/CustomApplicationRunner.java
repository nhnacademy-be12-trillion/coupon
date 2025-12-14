package com.nhnacademy.coupon;

import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomApplicationRunner implements ApplicationRunner {
    private static final String WELCOME = "WELCOME";
    private final CouponPolicyJpaRepository couponPolicyJpaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!couponPolicyJpaRepository.existsByName(WELCOME))
            couponPolicyJpaRepository.save(new CouponPolicyJpaEntity(WELCOME,10_000D,50_000L,null,
                    CouponDiscountType.FIXED_AMOUNT));

    }
}
