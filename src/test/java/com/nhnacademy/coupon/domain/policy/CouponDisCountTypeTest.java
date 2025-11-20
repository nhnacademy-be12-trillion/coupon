package com.nhnacademy.coupon.domain.policy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponDisCountTypeTest {
    @Test
    @DisplayName("정해진 금액할인은 금액할인이다")
    void test(){
        Assertions.assertThat(CouponDisCountType.FIXED_AMOUNT.getSalePrice(100D,new Price(100L)).value()).isEqualTo(100L);
    }
    @Test
    @DisplayName("할인율 금액할인은 할인율이 적용된다.")
    void test1(){
        Assertions.assertThat(CouponDisCountType.RATE.getSalePrice(0.1D,new Price(100L)).value()).isEqualTo(10L);
    }

}