package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.service.CouponService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {
    private final CouponService couponService;
    @GetMapping("")
    public Collection<Coupon> getCouponResponses(Pageable pageable){
        return couponService.findAll(pageable);
    }

    @PostMapping("/{couponId}/use")
    public void issueCoupon(@PathVariable Long couponId, Long memberId, Book book) {
        couponService.useCoupon(couponId,memberId,book);
    }
    @PostMapping("")
    public void issueWelcomeCoupon(Long memberId) {
        couponService.issueWelcomeCoupon(memberId);
    }
    @DeleteMapping("/{couponId}/use")
    public void rollbackCoupon(@PathVariable Long couponId, Long memberId) {
        couponService.rollbackCoupon(couponId,memberId);
    }
}
