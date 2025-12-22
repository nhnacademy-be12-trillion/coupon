package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.policy.Price;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Book {
    private Long bookId;
    private String isbn;
    private Long quantity;
    private Price price;
    private Set<Long> categoryIds;

    public Book(Long bookId, String isbn, Long quantity, Long price, List<Long> categoryIds) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = new Price(price);
        this.categoryIds= categoryIds.stream().collect(Collectors.toUnmodifiableSet());
    }
}
