package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CategoryIdCoupon extends Coupon{
    private Long categoryId;
    public CategoryIdCoupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                            LocalDateTime issueEndDate, Long categoryId) {
        super(id, name, policyId, quantity, issueStartDate, issueEndDate);
        if(categoryId == null) {
            throw new CustomException("error.message.notFoundCoupon");
        }
        this.categoryId = categoryId;
    }

    @Override
    public void validateCoupon(Book book, Long usingCount, LocalDateTime now) {
        super.validateCoupon(book, usingCount, now);
        if(!Objects.equals(categoryId, book.categoryId()))
            throw new CustomException("error.message.notFoundCoupon");

    }
}
