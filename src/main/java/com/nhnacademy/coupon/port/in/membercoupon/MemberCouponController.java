package com.nhnacademy.coupon.port.in.membercoupon;

import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.service.MemberCoupon;
import com.nhnacademy.coupon.service.MemberCouponService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member-coupons")
public class MemberCouponController {
    private final MemberCouponService service;

    @GetMapping("")
    public Collection<MemberCoupon> getUserCoupons(@MemberId Long memberId, Pageable pageable){
        return service.findAll(memberId,pageable);
    }
    @PostMapping("")
    public void saveUserCoupon(@MemberId Long memberId, @RequestBody MemberCouponCreateRequest request){
        service.save(new MemberCoupon(memberId,request.couponId()));
    }
}
