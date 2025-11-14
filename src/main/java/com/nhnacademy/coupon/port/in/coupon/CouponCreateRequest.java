package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.service.coupon.CategoryCoupon;
import com.nhnacademy.coupon.service.coupon.Coupon;
import com.nhnacademy.coupon.service.coupon.CouponType;
import com.nhnacademy.coupon.service.coupon.NameCoupon;
import java.time.LocalDateTime;

record CouponCreateRequest(String name, Long policyId, Long quantity, LocalDateTime issueStartDate, LocalDateTime issueEndDate,
                           CouponType type,String bookName,String categoryName) {
    Coupon create(){
        if(type == CouponType.DEFAULT) {
            return new Coupon(null,name,policyId,quantity,issueStartDate,issueEndDate);
        }
        if(type == CouponType.NAME) {
            return new NameCoupon(null,name,policyId,quantity,issueStartDate,issueEndDate,bookName);
        }
        if(type == CouponType.CATEGORY) {
            return new CategoryCoupon(null,name,policyId,quantity,issueStartDate,issueEndDate,categoryName);
        }
        throw new CustomException("error.message.notFoundCoupon");
    }
}
