package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import com.nhnacademy.coupon.service.policy.CouponPolicyService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponJpaRepository couponJpaRepository;
    private final CouponPolicyService couponPolicyService;
    private final MakerComposite makerComposite;
    private final MemberCouponJpaRepository memberCouponJpaRepository;


    public Collection<Coupon> findAll(Pageable pageable) {
        return couponJpaRepository.findAll(pageable)
                .stream()
                .map(makerComposite::makeCoupon)
                .toList();
    }
    @Transactional
    public void save(Coupon coupon) {
        validateExistCouponPolicy(coupon);
        couponJpaRepository.save(makerComposite.makeCouponEntity(coupon));
    }

    private void validateExistCouponPolicy(Coupon coupon) {
      couponPolicyService.validateExistById(coupon.getPolicyId());
    }

    @Transactional
    public void update(Coupon coupon) {
        validateExistCouponPolicy(coupon);
        if(!couponJpaRepository.existsById(coupon.getId()))
            throw new CustomException("error.message.notFoundCouponId",new Object[]{coupon.getId()});
        couponJpaRepository.save(makerComposite.makeCouponEntity(coupon));
    }
    @Transactional
    public void useCoupon(Long couponId, Long memberId, Book book) {
        CouponJpaEntity couponJpaEntity = couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CustomException("error.message.notFoundCouponId", new Object[]{couponId,memberId}));
        Coupon coupon = makerComposite.makeCoupon(couponJpaEntity);
        Long usingCount= memberCouponJpaRepository.findByUsingCouponIdWithLock(couponId);
        coupon.validateCoupon(book, usingCount);

        couponPolicyService.validatePolicy(coupon.getPolicyId(),new Price(book.price()));

        memberCouponJpaRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new CustomException("error.message.notFoundMemberCouponId", new Object[]{couponId, memberId}))
                .useCoupon();
    }

    @Transactional
    public void rollbackCoupon(Long couponId, Long memberId) {
        memberCouponJpaRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new CustomException("error.message.notFoundMemberCouponId", new Object[]{couponId, memberId}))
                .rollback();
    }
}
