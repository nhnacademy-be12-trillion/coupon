package com.nhnacademy.coupon.port.in.membercoupon;

import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.port.out.MemberCouponQuerydsl;
import com.nhnacademy.coupon.port.out.MemberCouponResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//해당객체는 레포지토리로 바로 값을 찾는 로직을 포함하고있음.
@RestController
@RequiredArgsConstructor
@RequestMapping("/member-coupons")
public class MemberCouponQueryController {
    private final MemberCouponQuerydsl service;

    @GetMapping("")
    public List<MemberCouponResponse> getMemberCoupons(@RequestParam(required = false,defaultValue = "false")boolean isUse, @MemberId Long memberId, Pageable pageable){
        return service.getCoupons(isUse,memberId,pageable);
    }
}
