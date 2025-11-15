package com.nhnacademy.coupon.port.out;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCouponJpaEntity,Long> {
    List<MemberCouponJpaEntity> findAllByMemberId(Long memberId, Pageable pageable);
}
