package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponDiscountTypeTest {
    @Test
    @DisplayName("정량 금액할인은 금액할인이다")
    void test(){
        Assertions.assertThat(CouponDiscountType.FIXED_AMOUNT.getDiscountAmount(100D,new Price(100L)).value()).isEqualTo(100L);
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.1,0.002,-1,0})
    @DisplayName("정량 금액할인에 소수점이나 양수가아니면 예외를 반환한다.")
    void test2(double value){
        Assertions.assertThatThrownBy(()-> CouponDiscountType.FIXED_AMOUNT.getDiscountAmount(value,new Price(100L))).isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(doubles = {101})
    @DisplayName("정량 금액할인이 가격보다 크면 예외를 반환한다.")
    void test3(double value){
        Assertions.assertThatThrownBy(()-> CouponDiscountType.FIXED_AMOUNT.getDiscountAmount(value,new Price(100L))).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("할인율 금액할인은 할인율이 적용된다.")
    void test1(){
        Assertions.assertThat(CouponDiscountType.RATE.getDiscountAmount(0.1D,new Price(100L)).value()).isEqualTo(10L);
    }
    @ParameterizedTest
    @ValueSource(doubles = {0,1})
    @DisplayName("할인율 금액할인은 0보다 크고,1 보다 작지않으면 예외를 발생한다.")
    void test1(double value){
        Assertions.assertThatThrownBy(()-> CouponDiscountType.RATE.getDiscountAmount(value,new Price(100L))).isInstanceOf(CustomException.class);
    }
}