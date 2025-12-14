package com.nhnacademy.coupon.port.out;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface MemberCouponJpaRepository extends JpaRepository<MemberCouponJpaEntity,Long> {
    List<MemberCouponJpaEntity> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<MemberCouponJpaEntity> findByCouponIdAndMemberId(Long couponId, Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select count(*) from MemberCouponJpaEntity  m where m.couponId=:couponId and m.use=true")
    Long findByUsingCouponIdWithLock(Long couponId);

}
