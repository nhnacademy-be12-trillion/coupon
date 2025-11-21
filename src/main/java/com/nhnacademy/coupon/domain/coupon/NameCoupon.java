package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.error.CustomException;
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

    @Override
    public void validateCoupon(Book book, Long usingCount) {
        super.validateCoupon(book, usingCount);
        if(!book.name().equals(bookName))
            throw new CustomException("error.message.notFoundCoupon");
    }
}
