package com.nhnacademy.coupon.port.in.admin.coupon;

import com.nhnacademy.coupon.port.in.admin.coupon.maker.RequestMakerComposite;
import com.nhnacademy.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class AdminCouponController {
    private final CouponService couponService;
    private final RequestMakerComposite requestMakerComposite;


    @PostMapping
    public void createCoupon(@RequestBody CouponRequestImpl couponRequestImpl) {
        couponService.save(requestMakerComposite.make(null, couponRequestImpl));
    }
    @PutMapping("/{couponId}")
    public void updateCoupon(@PathVariable Long couponId, @RequestBody CouponRequestImpl couponRequestImpl) {
        couponService.update(requestMakerComposite.make(couponId, couponRequestImpl));
    }
}
