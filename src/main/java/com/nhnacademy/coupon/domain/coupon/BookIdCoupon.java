package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
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
    public boolean isAvailable(Book book) {
        return bookId.equals(book.getBookId());
    }
}
