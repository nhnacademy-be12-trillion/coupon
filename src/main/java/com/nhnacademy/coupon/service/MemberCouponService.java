package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private final MemberCouponJpaRepository repository;
    private final CouponJpaRepository couponJpaRepository;
    public Collection<MemberCoupon> findAll(Long memberId, Pageable pageable) {
        return repository.findAllByMemberId(memberId,pageable)
                .stream()
                .map(entity->new MemberCoupon(entity.getId(),entity.getMemberId(),entity.getCouponId(),entity.isUse(),entity.getLastModifiedDate()))
                .toList();
    }
    @Transactional
    public void save(MemberCoupon memberCoupon) {
        if(!couponJpaRepository.existsById(memberCoupon.getCouponId()))
            throw new CustomException("error.message.notFoundCoupon");
        repository.save(new MemberCouponJpaEntity(memberCoupon.getMemberId(),memberCoupon.getCouponId()));
    }
}
