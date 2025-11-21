package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AllPricePolicyTest {
    @Test
    @DisplayName("최소금액을 넘지 않는경우 예외를 반환한다.")
    void testGetMaxDiscountPrice() {
        CouponPolicy policy = new AllPricePolicy(1L,"qwe",200L,1000L,0.20D,CouponDisCountType.RATE);
        Assertions.assertThatThrownBy(()->policy.getSalePrice(new Price(100L))).isInstanceOf(CustomException.class);
    }
    @Test
    @DisplayName("최대 할인금액이 넘으면 최대할인금액만 준다.")
    void testGetMaxDiscountPrice1() {
        CouponPolicy policy = new AllPricePolicy(1L,"qwe",100L,10L,0.90D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.getSalePrice(new Price(100L)).value()).isEqualTo(10L);
    }

    @Test
    @DisplayName("할인금액은 최대할인금액보다 작으면, 할인금액을 준다.")
    void test2() {
        CouponPolicy policy = new AllPricePolicy(1L,"qwe",100L,1000L,0.1D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.getSalePrice(new Price(100L)).value()).isEqualTo(10L);
    }
    @Test
    @DisplayName("최대할인금액과 최소금액이 있는경우 동작된다")
    void test3() {
        CouponPolicy policy = new AllPricePolicy(1L,"qwe",100L,1000L,0.1D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.match()).isTrue();
    }
}