package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class BookIdCoupon extends Coupon{
    private Long bookId;
    public BookIdCoupon(Long id, String name, Long policyId, Long quantity, LocalDateTime issueStartDate,
                        LocalDateTime issueEndDate, Long bookId) {
        super(id, name, policyId, quantity, issueStartDate, issueEndDate);
        if(bookId == null) {
            throw new CustomException("error.message.notFoundCoupon");
        }
        this.bookId = bookId;
    }

    @Override
    public void validateCoupon(Book book, Long usingCount, LocalDateTime now) {
        super.validateCoupon(book, usingCount, now);
        if(!Objects.equals(book.bookId(), bookId))
            throw new CustomException("error.message.notFoundCoupon");
    }
}
