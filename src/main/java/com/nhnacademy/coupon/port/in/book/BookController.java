package com.nhnacademy.coupon.port.in.book;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.service.BookCouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-coupons")
public class BookController {
    private final BookCouponService couponService;

    @GetMapping("{book-id}")
    public List<Coupon> getBookCoupons(@PathVariable("book-id") Long bookId, Pageable pageable){
        return couponService.getCoupons(bookId,pageable);
    }

}
