package com.nhnacademy.coupon.port.in.membercoupon;

import com.nhnacademy.coupon.service.MemberCoupon;
import com.nhnacademy.coupon.service.MemberCouponService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCouponController {
    private final MemberCouponService service;

    @GetMapping("members/{memberId}/coupons")
    public Collection<MemberCoupon> getUserCoupon(@PathVariable Long memberId, Pageable pageable){
        return service.findAll(memberId,pageable);
    }
    @PostMapping("members/{memberId}/coupons")
    public void saveUserCoupon(@PathVariable Long memberId, @RequestBody MemberCouponCreateRequest request){
        service.save(new MemberCoupon(memberId,request.couponId()));
    }
}
