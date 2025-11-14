package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {
    @Test
    @DisplayName("끝나는 시간이 시작시간보다 앞이면 예외반환")
    void testCoupon() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.minusSeconds(1);

        Assertions.assertThatThrownBy(()->new Coupon(null,"qwe",1L,null,start,end)).isInstanceOf(CustomException.class);
    }
    @Test
    @DisplayName("끝나는 시간이 시작시간이 같으면 예외반환")
    void testCoupon1() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start;

        Assertions.assertThatCode(()->new Coupon(null,"qwe",1L,null,start,end)).isInstanceOf(CustomException.class);
    }

    @Test
    @DisplayName("정상적으로 생성된다.")
    void testCoupon2() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);

        Assertions.assertThatCode(()->new Coupon(null,"qwe",1L,null,start,end)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("쿠폰퀄리티가 null이면 활성화된다.")
    void activeTest(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);
        boolean except = true;
        Coupon coupon = new Coupon(null, "qwe", 1L, null, start, end);
        Assertions.assertThat(coupon.isActive()).isEqualTo(except);
    }
    @ParameterizedTest
    @ValueSource(longs = {1,Integer.MAX_VALUE,Long.MAX_VALUE})
    @DisplayName("쿠폰퀄리티가 양수면 활성화가 된다.")
    void activeTest4(long value){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);
        boolean except = true;
        Coupon coupon = new Coupon(null, "qwe", 1L, value, start, end);
        Assertions.assertThat(coupon.isActive()).isEqualTo(except);
    }


    @ParameterizedTest
    @ValueSource(longs = {-1,0})
    @DisplayName("쿠폰퀄리티가 양수가 아니면 활성화가 안된다.")
    void activeTest1(long value){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);
        boolean except = false;
        Coupon coupon = new Coupon(null, "qwe", 1L, value, start, end);
        Assertions.assertThat(coupon.isActive()).isEqualTo(except);
    }
}