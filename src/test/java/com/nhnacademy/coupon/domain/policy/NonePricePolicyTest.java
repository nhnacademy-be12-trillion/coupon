package com.nhnacademy.coupon.domain.policy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NonePricePolicyTest {
    @Test
    @DisplayName("최소 주문과 최대 할인폭이 널인 경우 할인값이 나온다.")
    void testGetDiscountValue() {
        CouponPolicy policy = new NonePricePolicy(1L,"qwe",null,null,0.90D,CouponDisCountType.RATE);
        Assertions.assertThat(policy.match()).isTrue();
        Assertions.assertThat(policy.getSalePrice(new Price(100L)).value()).isEqualTo(90L);
    }

}