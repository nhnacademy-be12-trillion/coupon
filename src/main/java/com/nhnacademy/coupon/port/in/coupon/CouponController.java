package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.service.CouponService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @GetMapping("/{coupon-id}")
    public Price getSalePrice(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId, Book book) {
        return couponService.getCouponPolicy(couponId).getApplyCouponPrice(book.getPrice());
    }

    @PostMapping("/{coupon-id}/use")
    public void useCoupon(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId, @RequestBody Book book) {
        couponService.useCoupon(couponId,memberId,book);
    }
    @PostMapping("/welcome")
    public void issueWelcomeCoupon(@MemberId Long memberId) {
        couponService.issueWelcomeCoupon(memberId);
    }
    @DeleteMapping("/{coupon-id}/use")
    public void rollbackCoupon(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId) {
        couponService.rollbackCoupon(couponId,memberId);
    }
}
