package com.nhnacademy.coupon.service.coupon;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NameCoupon extends Coupon{
    private String bookName;
    public NameCoupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                      LocalDateTime issueEndDate,String bookName) {
        super(id, name, policyId, quantity, issueStartDate, issueEndDate);
        this.bookName = bookName;
    }
    CouponType getType(){
        return CouponType.NAME;
    }
}
