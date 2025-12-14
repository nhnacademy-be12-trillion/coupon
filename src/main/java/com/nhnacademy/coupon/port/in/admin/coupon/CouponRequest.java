package com.nhnacademy.coupon.port.in.admin.coupon;

import java.time.LocalDateTime;

public interface CouponRequest {

    Long categoryId();

    Long bookId();

    LocalDateTime issueEndDate();

    LocalDateTime issueStartDate();

    Long quantity();

    Long policyId();

    String name();
}
