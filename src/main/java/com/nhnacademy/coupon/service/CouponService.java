package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponJpaRepository couponJpaRepository;
    private final CouponPolicyJpaRepository couponPolicyJpaRepository;
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
        if(!couponPolicyJpaRepository.existsById(coupon.getPolicyId()))
            throw new CustomException("error.message.notFoundCouponPolicy");
        makerComposite.save(coupon);
    }
    @Transactional
    public void update(Coupon coupon) {
        if(!couponPolicyJpaRepository.existsById(coupon.getPolicyId()))
            throw new CustomException("error.message.notFoundCouponPolicy");
        if(!couponJpaRepository.existsById(coupon.getId()))
            throw new CustomException("error.message.notFoundCouponId",new Object[]{coupon.getId()});
        makerComposite.save(coupon);
    }
    @Transactional
    public void useCoupon(Long couponId, Long memberId, Book book) {
        Long usingCount= memberCouponJpaRepository.findByUsingCouponIdWithLock(couponId);
        CouponJpaEntity couponJpaEntity = couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CustomException("error.message.notFoundCouponId", new Object[]{couponId,memberId}));

        validateMemberCoupon(book, couponJpaEntity,usingCount);
        memberCouponJpaRepository.findByCouponIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new CustomException("error.message.notFoundMemberCouponId", new Object[]{couponId, memberId}))
                .useCoupon();
    }

    private void validateMemberCoupon(Book book, CouponJpaEntity couponJpaEntity,Long usingCount) {
        if(couponJpaEntity.getQuantity()-usingCount<0||!couponJpaEntity.isAvailable(LocalDateTime.now(), book))
            throw new CustomException("error.message.notUseMemberCoupon");
    }
}
