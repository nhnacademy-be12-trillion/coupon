package com.nhnacademy.coupon.port.in.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.in.coupon.CouponRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMakerComposite {
    private final List<CouponRequestMaker> couponRequestMakers;
    public Coupon make(Long id,CouponRequest request) {
        return couponRequestMakers.stream()
                .filter(requestMaker -> requestMaker.match(request))
                .findAny()
                .orElseThrow(()->new CustomException("error.message.notFoundCouponMaker",new Object[]{String.valueOf(request.type())}))
                .make(id,request);
    }
}
