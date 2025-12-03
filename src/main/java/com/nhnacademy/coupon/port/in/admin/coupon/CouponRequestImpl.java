package com.nhnacademy.coupon.port.in.admin.coupon;

import java.time.LocalDateTime;

record CouponRequestImpl(String name, Long policyId, Long quantity, LocalDateTime issueStartDate, LocalDateTime issueEndDate, String bookName, String categoryName) implements CouponRequest {
}
