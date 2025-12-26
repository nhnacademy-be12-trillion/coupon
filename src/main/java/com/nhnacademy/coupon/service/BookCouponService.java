package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.out.CouponQueryDsl;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCouponService {
    private final MakerComposite makerComposite;
    private final CouponQueryDsl couponQUeryDsl;
    private final CheckCouponService checkCouponService;
    @Transactional(readOnly = true)
    public List<Coupon> getCoupons(Long bookId, Pageable pageable) {
        return couponQUeryDsl.findCouponBook(bookId,checkCouponService.filterAvailableBook(bookId).getCategoryIds(), pageable)
                        .stream()
                        .map(makerComposite::makeCoupon)
                        .toList();
    }
}
