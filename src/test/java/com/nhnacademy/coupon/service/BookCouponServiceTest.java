package com.nhnacademy.coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.domain.coupon.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.port.in.admin.coupon.maker.RequestMakerComposite;
import com.nhnacademy.coupon.port.out.CouponQueryDsl;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import com.nhnacademy.coupon.service.maker.MakerCompositeConfig;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    @InjectMocks
    private BookCouponService bookCouponService;
    @Mock
    private CouponQueryDsl couponQUeryDsl;
    @Mock
    private CheckCouponService checkCouponService;
    @Mock
    private MemberCouponJpaRepository couponJpaRepository;
    @Mock
    private MemberCouponJpaRepository memberCouponJpaRepository;
    @Mock
    private CouponQueryDsl couponQueryDsl;
    @BeforeEach
    void setUp() {
        bookCouponService= new BookCouponService(makerComposite, couponQUeryDsl, checkCouponService,couponJpaRepository);
    }

    @Test
    @DisplayName("getCoupons 테스트")
    void test1(){
        Coupon coupon = new Coupon(1L, "test", 1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1));
        when(checkCouponService.filterAvailableBook(1L)).thenReturn(new Book(1L,"123",1L,1L,List.of(1L,2L)));
        when(couponQUeryDsl.findCouponBook(any(),anySet(),any())).thenReturn(List.of(
                new CouponJpaEntity(coupon)
        ));

        Assertions.assertThat(bookCouponService.getCoupons(1L, PageRequest.of(0, 10)))
                .hasSize(1);
        Assertions.assertThat(bookCouponService.getCoupons(1L, PageRequest.of(0, 10)).getFirst().getId())
                .isEqualTo(coupon.getId());
    }
}
