package com.nhnacademy.coupon.domain.policy;

import com.nhnacademy.coupon.error.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {
    @Test
    @DisplayName("가격은 음수이면안된다.")
    void test1() {
        Assertions.assertThatCode(() -> new Price(-1L)).isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(longs = {0,1})
    @DisplayName("가격은 음수가아니면 된다.")
    void test1(long value) {
        Assertions.assertThatCode(() -> new Price(value)).doesNotThrowAnyException();
    }
}