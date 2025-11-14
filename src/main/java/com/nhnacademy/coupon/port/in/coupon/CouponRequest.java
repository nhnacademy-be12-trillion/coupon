package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.domain.coupon.CouponType;
import java.time.LocalDateTime;

public interface CouponRequest {

    String categoryName();

    String bookName();

    LocalDateTime issueEndDate();

    LocalDateTime issueStartDate();

    Long quantity();

    Long policyId();

    String name();

    CouponType type();
}
