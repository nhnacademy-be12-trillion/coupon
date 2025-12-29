package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long memberCouponId, String name, Long quantity, LocalDateTime issueStartDate, LocalDateTime issueEndDate, Long minOrderPrice, Long maxDiscountPrice,
                                   CouponDiscountType couponDiscountType) {
}
