package com.nhnacademy.coupon.port.in.policy;

import com.nhnacademy.coupon.service.CouponPolicy;
import com.nhnacademy.coupon.service.CouponPolicyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/policies")
public class CouponPolicyController {
    private final CouponPolicyService service;
    @GetMapping()
    public List<CouponPolicy> getCouponPolicyResponse(Pageable pageable){
        return service.getCouponPolicys(pageable);
    }
    @PostMapping
    public void createCouponPolicy(@RequestBody PolicyCreateRequest request) {
        service.save(request.createCouponPolicy());
    }
}
