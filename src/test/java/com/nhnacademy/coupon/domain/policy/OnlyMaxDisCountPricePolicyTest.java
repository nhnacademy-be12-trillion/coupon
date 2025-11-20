package com.nhnacademy.coupon.domain.policy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnlyMaxDisCountPricePolicyTest {
    @Test
    @DisplayName("최대 할인금액이 넘으면 최대할인금액만 준다.")
    void testGetMaxDiscountPrice1() {
        CouponPolicy policy = new OnlyMaxDisCountPricePolicy(1L,100L,10L,0.90D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.getSalePrice(new Price(100L)).value()).isEqualTo(10L);
    }

    @Test
    @DisplayName("할인금액은 최대할인금액보다 작으면, 할인금액을 준다.")
    void test2() {
        CouponPolicy policy = new OnlyMaxDisCountPricePolicy(1L,100L,1000L,0.1D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.getSalePrice(new Price(100L)).value()).isEqualTo(10L);
    }
    @Test
    @DisplayName("최대 할인금액은 있고, 최소할인금액이 없으면 작동한다.")
    void test3() {
        CouponPolicy policy = new OnlyMaxDisCountPricePolicy(1L,null,1000L,0.1D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.match()).isTrue();
    }

}