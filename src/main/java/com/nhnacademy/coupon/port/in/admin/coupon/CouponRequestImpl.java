package com.nhnacademy.coupon.port.in.admin.coupon;

import com.nhnacademy.coupon.domain.coupon.CouponType;
import java.time.LocalDateTime;

record CouponRequestImpl(String name, Long policyId, Long quantity, LocalDateTime issueStartDate, LocalDateTime issueEndDate,
                         CouponType type, String bookName, String categoryName) implements CouponRequest {
}
