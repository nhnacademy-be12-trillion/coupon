package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnlyMinOrderPriceCouponPolicyTest {
    @Test
    @DisplayName("최소금액을 넘지 않는경우 예외를 반환한다.")
    void testGetMaxDiscountPrice() {
        CouponPolicy policy = new OnlyMinOrderPriceCouponPolicy(1L,"qwe",200L,1000L,0.20D, CouponDiscountType.RATE);
        Assertions.assertThatThrownBy(()->policy.getDiscountAmount(new Price(100L))).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("최소금액을 넘지 않는경우 할인금액만큼 반환한다.")
    void testGetMaxDiscountPrice1() {
        CouponPolicy policy = new OnlyMinOrderPriceCouponPolicy(1L,"qwe",100L,1000L,0.20D, CouponDiscountType.RATE);
        Assertions.assertThat(policy.getDiscountAmount(new Price(100L)).value()).isEqualTo(20L);
    }
    @Test
    @DisplayName("최대 할인금액은 없고, 최소할인금액이 있으면 작동한다.")
    void test3() {
        CouponPolicy policy = new OnlyMinOrderPriceCouponPolicy(1L,"qwe",100L,null,0.1D, CouponDiscountType.RATE);
        Assertions.assertThat(policy.match()).isTrue();
    }
}