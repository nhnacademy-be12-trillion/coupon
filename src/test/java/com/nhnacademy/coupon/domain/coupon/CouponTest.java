package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.error.CustomException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book= new Book(1L,"qwe", "qwe");
    }
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

        Assertions.assertThatThrownBy(()->new Coupon(null,"qwe",1L,null,start,start)).isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(longs = {-1,0})
    @DisplayName("쿠폰 수량이 양수가 아니면 예외반환")
    void testCoupon4(long quantity) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);

        Assertions.assertThatThrownBy(()->new Coupon(null,"qwe",1L,quantity,start,end)).isInstanceOf(CustomException.class);
    }


    @Test
    @DisplayName("정상적으로 생성된다.")
    void testCoupon2() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);

        Assertions.assertThatCode(()->new Coupon(null,"qwe",1L,null,start,end)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(longs = {1,2,Long.MAX_VALUE})
    @DisplayName("쿠폰 수량이 null이면 활성화된다.")
    void activeTest(long usingCount){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(2);
        Coupon coupon = new Coupon(null, "qwe", 1L, null, start, end);
        Assertions.assertThatCode(()->coupon.validateCoupon(book,usingCount,start.plusSeconds(1))).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(longs = {0,1,10})
    @DisplayName("쿠폰 수량이 사용량보다 크면 활성화가 된다.")
    void activeTest4(long usingCount){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(2);
        Coupon coupon = new Coupon(null, "qwe", 1L, 10L, start, end);
        Assertions.assertThatCode(()->coupon.validateCoupon(book,usingCount, start.plusSeconds(1))).doesNotThrowAnyException();
    }


    @ParameterizedTest
    @ValueSource(longs = {11,Integer.MAX_VALUE,Long.MAX_VALUE})
    @DisplayName("사용량이 쿠폰수량보다 많으면 활성화가 안된다.")
    void activeTest1(long usingCount){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);
        Coupon coupon = new Coupon(null, "qwe", 1L, 10L, start, end);
        Assertions.assertThatThrownBy(()->coupon.validateCoupon(book,usingCount, null)).isInstanceOf(CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Integer.MIN_VALUE,-1})
    @DisplayName("사용량이 음수이면 활성화가 안된다.")
    void activeTest10(long usingCount){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end= start.plusSeconds(1);
        Coupon coupon = new Coupon(null, "qwe", 1L, null, start, end);
        Assertions.assertThatThrownBy(()->coupon.validateCoupon(book,usingCount, null)).isInstanceOf(CustomException.class);
    }
}