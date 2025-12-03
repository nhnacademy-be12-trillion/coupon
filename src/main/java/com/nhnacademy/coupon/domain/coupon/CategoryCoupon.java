package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CategoryCoupon extends Coupon{
    private String categoryName;
    public CategoryCoupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                          LocalDateTime issueEndDate,String categoryName) {
        super(id, name, policyId, quantity, issueStartDate, issueEndDate);
        if(categoryName == null) {
            throw new CustomException("error.message.notFoundCoupon");
        }
        this.categoryName = categoryName;
    }

    @Override
    public void validateCoupon(Book book, Long usingCount, LocalDateTime now) {
        super.validateCoupon(book, usingCount, now);
        if(!categoryName.equals(book.category()))
            throw new CustomException("error.message.notFoundCoupon");

    }
}
