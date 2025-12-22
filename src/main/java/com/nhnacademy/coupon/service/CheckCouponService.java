package com.nhnacademy.coupon.service;

import com.nhnacademy.coupon.domain.coupon.Book;
import com.nhnacademy.coupon.domain.coupon.BookOrder;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.BookClient;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckCouponService {
    private final MakerComposite makerComposite;
    private final CouponJpaRepository couponJpaRepository;
    private final BookClient bookClient;


    @Transactional(readOnly = true)
    public List<Book> filterAvailableBook(List<BookOrder> bookOrders, Long couponId) {
        Coupon coupon = makerComposite.makeCoupon(couponJpaRepository.findById(couponId)
                .orElseThrow(() -> new CustomException("error.message.notFoundCouponId", new Object[]{couponId})));

        List<Long> bookIds = bookOrders.stream().map(BookOrder::bookId).toList();

        return bookClient.getBooks(bookIds)
                .stream()
                .map(response -> new Book(response.bookId(),response.isbn(),response.bookStock(),response.bookSalePrice() ,response.categoryIds()))
                .filter(coupon::isAvailable)
                .toList();
    }
}
