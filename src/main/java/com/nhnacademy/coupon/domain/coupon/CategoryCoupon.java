package com.nhnacademy.coupon.domain.coupon;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CategoryCoupon extends Coupon{
    private String categoryName;
    public CategoryCoupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                          LocalDateTime issueEndDate,String categoryName) {
        super(id, name, policyId, quantity, issueStartDate, issueEndDate);
        this.categoryName = categoryName;
    }
    @Override
    public CouponType getType(){
        return CouponType.CATEGORY;
    }
}
