package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import com.nhnacademy.coupon.domain.coupon.BookIdCoupon;
import com.nhnacademy.coupon.domain.coupon.CategoryIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestMakeRequestConfig.class)
@Import(value = {RequestMakerComposite.class})
class RequestMakerCompositeTest {
    @Autowired
    RequestMakerComposite composite;

    @Test
    @DisplayName("기본값은 Coupon이 나온다.")
    void testMakeRequest() {
        Assertions.assertThat(
        composite.make(1L,new TestCouponRequestImpl(
           "qwe",
           1L,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(1),
                null,
                null
        ))
        ).isExactlyInstanceOf(Coupon.class);
    }
    @Test
    @DisplayName("카테고리값이 있으면, 카테고리쿠폰을 가리킨다.")
    void testMakeRequest1() {
        Assertions.assertThat(
                composite.make(1L,new TestCouponRequestImpl(
                        "qwe",
                        1L,
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1),
                        null,
                        1L
                ))
        ).isExactlyInstanceOf(CategoryIdCoupon.class);
    }
    @Test
    @DisplayName("책이름이 있으면, 이름쿠폰을 가리킨다.")
    void testMakeRequest2() {
        Assertions.assertThat(
                composite.make(1L,new TestCouponRequestImpl(
                        "qwe",
                        1L,
                        1L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds(1),
                        1L,
                        null
                ))
        ).isExactlyInstanceOf(BookIdCoupon.class);
    }
}
