package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.domain.coupon.BookDiscountPrice;
import com.nhnacademy.coupon.domain.coupon.BookOrder;
import com.nhnacademy.coupon.domain.policy.Price;
import java.util.List;
import lombok.Getter;

@Getter
class DiscountPriceResponse {
    private List<DiscountBookResponse> discountBooks;
    private Long totalDiscountPrice;

    DiscountPriceResponse(List<BookOrder> orders, BookDiscountPrice prices, Price discountPrice){
        this.discountBooks=orders.stream()
                .map(order-> new DiscountBookResponse(order.bookId(),prices.getPercentBookPrice(discountPrice,order.bookId()).value()))
                .toList();
        this.totalDiscountPrice=discountPrice.value();
    }
}
