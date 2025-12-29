package com.nhnacademy.coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.domain.coupon.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.maker.RequestMakerComposite;
import com.nhnacademy.coupon.port.out.CouponQueryDsl;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import com.nhnacademy.coupon.service.maker.MakerCompositeConfig;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MakerCompositeConfig.class)
@Import(value = {RequestMakerComposite.class})
class BookCouponServiceTest {
    @Autowired
    private MakerComposite  makerComposite;

    private BookCouponService bookCouponService;
    @Mock
    private CouponQueryDsl couponQUeryDsl;
    @Mock
    private CheckCouponService checkCouponService;

    @BeforeEach
    void setUp() {
        bookCouponService= new BookCouponService(makerComposite, couponQUeryDsl, checkCouponService);
    }

    @Test
    @DisplayName("getCoupons 테스트")
    void test1(){
        Coupon coupon = new Coupon(1L, "test", 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
        when(checkCouponService.filterAvailableBook(1L)).thenReturn(new Book(1L,"123",1L,1L,List.of(1L,2L)));
        when(couponQUeryDsl.findCouponBook(any(),any(),any())).thenReturn(List.of(
                new CouponJpaEntity(coupon)
        ));

        Assertions.assertThat(bookCouponService.getCoupons(1L, PageRequest.of(0, 10)))
                .hasSize(1);
        Assertions.assertThat(bookCouponService.getCoupons(1L, PageRequest.of(0, 10)).getFirst().getId())
                .isEqualTo(coupon.getId());

    }
}