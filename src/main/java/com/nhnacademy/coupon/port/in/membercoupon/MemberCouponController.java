package com.nhnacademy.coupon.port.in.membercoupon;

import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.service.MemberCoupon;
import com.nhnacademy.coupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-coupons")
public class MemberCouponController {
    private final MemberCouponService service;

    @PostMapping("")
    public void saveUserCoupon(@MemberId Long memberId, @RequestBody MemberCouponCreateRequest request){
        service.save(new MemberCoupon(memberId,request.couponId()));
    }
}
