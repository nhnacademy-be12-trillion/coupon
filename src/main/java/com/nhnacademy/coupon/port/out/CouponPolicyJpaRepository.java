package com.nhnacademy.coupon.port.out;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyJpaRepository extends JpaRepository<CouponPolicyJpaEntity, Long> {
    boolean existsByName(String name);
    Optional<CouponPolicyJpaEntity> findByName(String name);
}
