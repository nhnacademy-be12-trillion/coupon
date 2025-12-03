package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.port.in.admin.coupon.CouponRequest;
import java.time.LocalDateTime;

public record TestCouponRequestImpl(String name, Long policyId, Long quantity, LocalDateTime issueStartDate, LocalDateTime issueEndDate,
                                    String bookName, String categoryName) implements CouponRequest {
}
