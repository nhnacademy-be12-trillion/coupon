package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.coupon.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.out.CouponQueryDsl;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCouponService {
    private final MakerComposite makerComposite;
    private final CouponQueryDsl couponQueryDsl;
    private final CheckCouponService checkCouponService;
    private final MemberCouponJpaRepository memberCouponJpaRepository;

    @Transactional(readOnly = true)
    public List<Coupon> getCoupons(Long bookId, Pageable pageable) {
        return couponQueryDsl.findCouponBook(bookId,checkCouponService.filterAvailableBook(bookId).getCategoryIds(), pageable)
                        .stream()
                        .map(makerComposite::makeCoupon)
                        .toList();
    }
    @Transactional
    public long saveCoupon(Long memberId,Long bookId) {
        Book book = checkCouponService.filterAvailableBook(bookId);
        Set<Long> useCouponIds = memberCouponJpaRepository.findAllByMemberIdAndIsUse(memberId, true)
                .stream()
                .map(MemberCouponJpaEntity::getCouponId)
                .collect(Collectors.toSet());

        List<Long> noUsedCouponIds = couponQueryDsl.findCouponBook(memberId, bookId, book.getCategoryIds())
                .stream()
                .map(makerComposite::makeCoupon)
                .filter(coupon -> coupon.isAvailable(book))
                .map(Coupon::getId)
                .filter(couponId -> !useCouponIds.contains(couponId))
                .toList();
                noUsedCouponIds.forEach(coupon -> memberCouponJpaRepository.save(new  MemberCouponJpaEntity(memberId, coupon)));
        return noUsedCouponIds.size();
    }
}
