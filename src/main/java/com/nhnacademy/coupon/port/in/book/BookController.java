package com.nhnacademy.coupon.port.in.book;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.service.BookCouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-coupons")
@Slf4j
public class BookController {
    private final BookCouponService couponService;

    @GetMapping("{book-id}")
    public List<Coupon> getBookCoupons(@PathVariable("book-id") Long bookId, Pageable pageable){
        return couponService.getCoupons(bookId,pageable);
    }
    @PostMapping()
    public ResponseEntity<Long> saveBookCoupons(@MemberId Long memberId, @RequestBody BookCouponIssueRequest request){
        long saveCount = couponService.saveCoupon(memberId, request.bookId());
        if(saveCount == 0){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(saveCount);
    }

}
