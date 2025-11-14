package com.nhnacademy.coupon.service.policy;


import com.nhnacademy.coupon.error.CustomException;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponPolicyTest {
    @Test
    @DisplayName("할인 금액은 음수이면 안된다")
    void test1(){
        Arrays.stream(CouponDisCountType.values())
                .forEach(couponDisCountType ->
                        Assertions.assertThatThrownBy(
                                ()->new CouponPolicy(1L,-1d,1000L,200L,couponDisCountType))
                                .isInstanceOf(CustomException.class)
                );
    }


    @Test
    @DisplayName("금액할인때 할인은 소수점이 있으면 안된다.")
    void test(){
        Assertions.assertThatThrownBy(()->new CouponPolicy(1L,1.1d,1000L,200L,CouponDisCountType.FIXED_AMOUNT))
                .isInstanceOf(CustomException.class);
    }
    @Test
    @DisplayName("금액할인때 최대금액보다 할인금액이 크면 안된다.")
    void test2(){
        Assertions.assertThatThrownBy(()->new CouponPolicy(1L,201D,1000L,200L,CouponDisCountType.FIXED_AMOUNT))
                .isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(
            doubles = {-1,101}
    )
    @DisplayName("비율 할인은 0 보다 100 사이가 아니면 안된다.")
    void test1(double value){
        Assertions.assertThatThrownBy(()->new CouponPolicy(1L,value,1000L,200L,CouponDisCountType.RATE))
                .isInstanceOf(CustomException.class);
    }

}