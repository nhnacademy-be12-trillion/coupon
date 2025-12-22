package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.coupon.BookDiscountPrice;
import com.nhnacademy.coupon.domain.coupon.BookOrder;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import com.nhnacademy.coupon.service.policy.CouponPolicyService;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
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
    private final CheckCouponService checkCouponService;

    @Transactional(readOnly = true)
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
    public void useCoupon(Long couponId, Long memberId, List<BookOrder> bookOrders) {
        CouponJpaEntity couponJpaEntity = couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CustomException("error.message.notFoundCouponId", new Object[]{couponId,memberId}));

        Coupon coupon = makerComposite.makeCoupon(couponJpaEntity);
        Long usingCount= memberCouponJpaRepository.findByUsingCouponIdWithLock(couponId);
        BookDiscountPrice bookDiscountPrice = new BookDiscountPrice(bookOrders,checkCouponService.filterAvailableBook(bookOrders,couponId));

        coupon.validateCoupon(usingCount, LocalDateTime.now());
        couponPolicyService.validatePolicy(coupon.getPolicyId(),bookDiscountPrice.getTotalBookPrice());

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
    @Transactional
    public void issueWelcomeCoupon(Long memberId) {
        CouponPolicy welcomePolicy = couponPolicyService.getWelcomePolicy();
        LocalDateTime now = LocalDateTime.now();
        CouponJpaEntity entity= couponJpaRepository.save(new CouponJpaEntity(null, welcomePolicy.getName(), welcomePolicy.getId(), 1L, now, now.plusDays(30), null, null));
        memberCouponJpaRepository.save(new MemberCouponJpaEntity(memberId,entity.getId()));
    }
    @Transactional(readOnly = true)
    public Price getDiscountValue(Long couponId, List<BookOrder> bookOrders) {
        BookDiscountPrice bookDiscountPrice = new BookDiscountPrice(bookOrders,checkCouponService.filterAvailableBook(bookOrders,couponId));
        Coupon coupon = makerComposite.makeCoupon(couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CustomException("error.message.notFoundCouponId", new Object[]{couponId})));

        return couponPolicyService.getCouponPolicy(coupon.getPolicyId())
                .getDiscountAmount(bookDiscountPrice.getTotalBookPrice());
    }
}
