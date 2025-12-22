package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class BookDiscountPrice {
    private final Map<Long,BookOrder>  bookOrders;
    private final Map<Long, Book> books;

    public BookDiscountPrice(List<BookOrder> bookOrders, List<Book> books) {
        this.bookOrders = bookOrders.stream()
                .collect(Collectors.toMap(BookOrder::bookId,bookOrder -> bookOrder));
        this.books = books.stream()
                .collect(Collectors.toMap(Book::getBookId,book -> book));
        for (BookOrder bookOrder : bookOrders) {
            if(this.books.containsKey(bookOrder.bookId())&&bookOrder.quantities()>getBookQuantities(bookOrder.bookId())) {
                throw new CustomException("error.message.quantityOrder",new Object[]{bookOrder.quantities(),getBookQuantities(bookOrder.bookId())});
            }
        }

    }
    public Price getBookPrice(Long bookId) {
        return new Price(getBookSalePrice(bookId)* getOrderQuantities(bookId));
    }
    private Long getBookSalePrice(Long bookId) {
        if(!books.containsKey(bookId)) {
            return 0L;
        }
        return books.get(bookId).getPrice().value();
    }
    private Long getBookQuantities(Long bookId) {
        if(!books.containsKey(bookId)) {
            return 0L;
        }
        return books.get(bookId).getQuantity();
    }

    private Long getOrderQuantities(Long bookId) {
        if(!bookOrders.containsKey(bookId)) {
            return 0L;
        }
        return bookOrders.get(bookId).quantities();
    }

    public Price getTotalBookPrice() {
        return new Price(books.keySet()
                 .stream()
                 .mapToLong(bookId->getBookPrice(bookId).value())
                 .sum());
    }
    public Price getPercentBookPrice(Price discountPrice,Long bookId) {
        double totalValue = getTotalBookPrice().value();
        if (totalValue == 0) {
            return new Price(0L);
        }
        return new Price((long)(discountPrice.value()/totalValue*getBookPrice(bookId).value()));
    }


}
