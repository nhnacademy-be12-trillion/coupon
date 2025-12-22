package com.nhnacademy.coupon.port.in.coupon;

import com.nhnacademy.coupon.domain.coupon.BookDiscountPrice;
import com.nhnacademy.coupon.domain.coupon.BookOrder;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.infra.MemberId;
import com.nhnacademy.coupon.service.CheckCouponService;
import com.nhnacademy.coupon.service.CouponService;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {
    private final CouponService couponService;
    private final CheckCouponService checkCouponService;

    @GetMapping("")
    public Collection<Coupon> getCouponResponses(Pageable pageable){
        return couponService.findAll(pageable);
    }
    @GetMapping("/{coupon-id}")
    public DiscountPriceResponse getDiscountPrice(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId,
                                              @RequestParam List<Long>bookIds, @RequestParam List<Long>quantities) {
        List<BookOrder> bookOrders = getBookOrders(bookIds, quantities);
        return new DiscountPriceResponse(bookOrders,new BookDiscountPrice(bookOrders,checkCouponService.filterAvailableBook(bookOrders,couponId)),couponService.getDiscountValue(couponId, bookOrders));
    }
    @PostMapping("/{coupon-id}/use")
    public void useCoupon(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId, @RequestBody CouponUseRequest request) {
        List<BookOrder> bookOrders = getBookOrders(request.bookIds(), request.quantities());
        couponService.useCoupon(couponId,memberId,bookOrders);
    }
    @PostMapping("/welcome")
    public void issueWelcomeCoupon(@MemberId Long memberId) {
        couponService.issueWelcomeCoupon(memberId);
    }
    @DeleteMapping("/{coupon-id}/use")
    public void rollbackCoupon(@PathVariable("coupon-id") Long couponId, @MemberId Long memberId) {
        couponService.rollbackCoupon(couponId,memberId);
    }
    private @NonNull List<BookOrder> getBookOrders(List<Long> bookIds, List<Long> quantities) {
        if(bookIds.size() != quantities.size()) {
            throw new CustomException("error.message.notEqualSize",new Object[]{bookIds.size(), quantities.size()});
        }
        return IntStream.range(0, bookIds.size())
                .mapToObj(i -> new BookOrder(bookIds.get(i), quantities.get(i)))
                .toList();
    }

}
