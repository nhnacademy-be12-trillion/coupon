package com.nhnacademy.coupon.service;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCoupon {
    private Long id;
    private Long memberId;
    private Long couponId;
    private boolean use;
    private LocalDateTime lastModifiedDate;
}
