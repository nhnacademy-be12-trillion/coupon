package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponJpaRepository repository;
    public Collection<MemberCoupon> findAll(Long memberId, Pageable pageable) {
        return repository.findAllByMemberId(memberId,pageable)
                .stream()
                .map(entity->new MemberCoupon(entity.getId(),entity.getCouponId(),entity.getCouponId(),entity.isUse(),entity.getLastModifiedDate()))
                .toList();
    }
}
