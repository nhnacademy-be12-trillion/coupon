package com.nhnacademy.coupon.service.maker;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coupon {
    private Long id;
    private String name;
    private Long policyId;
    private Long quantity;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueEndDate;
}
