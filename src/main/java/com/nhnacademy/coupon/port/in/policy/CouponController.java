package com.nhnacademy.coupon.port.in.policy;

import com.nhnacademy.coupon.service.CouponPolicy;
import com.nhnacademy.coupon.service.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/policies")
public class CouponController {
    private final CouponService service;
    @GetMapping()
    public List<CouponPolicy> getCouponPolicyResponse(Pageable pageable){
        return service.getCouponPolicys(pageable);
    }
}
