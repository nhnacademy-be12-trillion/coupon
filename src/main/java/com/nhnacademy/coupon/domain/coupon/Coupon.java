package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Coupon {
    private Long id;
    private String name;
    private Long policyId;
    private Long quantity;
    private LocalDateTime issueStartDate;
    private LocalDateTime issueEndDate;

    public Coupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                  LocalDateTime issueEndDate) {
        if(issueEndDate.isBefore(issueStartDate)||issueEndDate.isEqual(issueStartDate)) {
            throw new CustomException("error.message.illegalDate",new Object[]{issueStartDate,issueEndDate});
        }
        this.id = id;
        this.name = name;
        this.policyId = policyId;
        this.quantity = quantity;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }

    public CouponType getType(){
        return CouponType.DEFAULT;
    }
    public boolean isActive(){
        if(quantity==null)
            return true;
        return quantity>0;
    }
}
