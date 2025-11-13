package com.nhnacademy.coupon.port.out.coupon;

import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponPolicyJpaEntity, Long> {
}
