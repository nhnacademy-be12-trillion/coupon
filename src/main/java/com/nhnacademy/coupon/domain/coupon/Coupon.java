package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
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
        if(quantity!=null&& quantity<=0){
            throw new CustomException("error.message.quantity",new Object[]{quantity});
        }

        this.id = id;
        this.name = name;
        this.policyId = policyId;
        this.quantity = quantity;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }
    public void validateCoupon(Book book,  Long usingCount) {
        LocalDateTime now = LocalDateTime.now();
        if(!compareQuantity(usingCount) || !(issueStartDate.isBefore(now) && now.isBefore(issueEndDate))
        ){
            throw new CustomException("error.message.notUseMemberCoupon");
        }
    }

    private boolean compareQuantity(Long usingCount) {
        if(usingCount<0)
            return false;
        if(quantity==null)
            return true;
        return quantity>=usingCount;
    }
}
