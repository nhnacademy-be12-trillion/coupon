package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponPolicyTest {

    @Test
    @DisplayName("할인값이 null이면 예외를 반환한다.")
    void test(){
        Assertions.assertThatThrownBy(()->new TestCouponPolicy(1L,null,null,null,null,CouponDisCountType.FIXED_AMOUNT))
                .isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(doubles = {-1,-0.01,0})
    @DisplayName("할인값이 양수가 아니면 예외를 반환한다.")
    void test1(double value){
        Assertions.assertThatThrownBy(()->new TestCouponPolicy(1L,null,null,null,value,CouponDisCountType.FIXED_AMOUNT))
                .isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.01,1})
    @DisplayName("나머지는 예외를 반환하지않는다.")
    void test2(double value){
        Assertions.assertThatCode(()->new TestCouponPolicy(1L,null,null,null,value,CouponDisCountType.FIXED_AMOUNT))
                .doesNotThrowAnyException();
    }
}

class TestCouponPolicy extends CouponPolicy {

    protected TestCouponPolicy(Long id, String name,Long minOrderPrice, Long maxDiscountPrice, Double discountValue,
                               CouponDisCountType couponDiscountType) {
        super(id, name,minOrderPrice, maxDiscountPrice, discountValue, couponDiscountType);
    }

    @Override
    public boolean match() {
        return false;
    }

    @Override
    protected Price getSalePrice(Price price) {
        return null;
    }
}