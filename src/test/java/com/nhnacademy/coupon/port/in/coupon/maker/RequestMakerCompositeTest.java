package com.nhnacademy.coupon.port.in.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.CategoryCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.coupon.CouponType;
import com.nhnacademy.coupon.domain.coupon.NameCoupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.in.coupon.TestCouponRequestImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestMakerCompositeTest {
    RequestMakerComposite composite;
    @BeforeEach
    void setUp() {
        composite=new RequestMakerComposite(
                List.of(
                new CategoryCouponRequestMaker(),
                new DefaultCouponRequestMaker(),
                new NameCouponRequestMaker()
        ));
    }
    @Test
    @DisplayName("모드가 기본이면, 쿠폰을 가리킨다.")
    void testMakeRequest() {
        Assertions.assertThat(
        composite.make(1L,new TestCouponRequestImpl(
           "qwe",
           1L,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(1),
                CouponType.DEFAULT,
                null,
                null
        ))
        ).isExactlyInstanceOf(Coupon.class);
    }
    @Test
    @DisplayName("모드가 카테고리이면, 카테고리쿠폰을 가리킨다.")
    void testMakeRequest1() {
        Assertions.assertThat(
                composite.make(1L,new TestCouponRequestImpl(
                        "qwe",
                        1L,
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1),
                        CouponType.CATEGORY,
                        null,
                        "qwe"
                ))
        ).isExactlyInstanceOf(CategoryCoupon.class);
    }
    @Test
    @DisplayName("모드가 이름이면, 이름쿠폰을 가리킨다.")
    void testMakeRequest2() {
        Assertions.assertThat(
                composite.make(1L,new TestCouponRequestImpl(
                        "qwe",
                        1L,
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1),
                        CouponType.NAME,
                        null,
                        "qwe"
                ))
        ).isExactlyInstanceOf(NameCoupon.class);
    }
    @Test
    @DisplayName("모드가 null이면, 예외를 반환한다.")
    void testMakeRequest3() {
        Assertions.assertThatThrownBy(
                ()->
                composite.make(1L,new TestCouponRequestImpl(
                        "qwe",
                        1L,
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1),
                        null,
                        null,
                        "qwe"
                ))
        ).isInstanceOf(CustomException.class);
    }


}
