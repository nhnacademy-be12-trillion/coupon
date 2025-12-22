package com.nhnacademy.coupon.domain.coupon;

import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BookDiscountPriceTest {
    private BookDiscountPrice bookDiscountPrice;

    @BeforeEach
    void setUp() {
        bookDiscountPrice =new BookDiscountPrice(List.of(
                new BookOrder(1L,2L)
                ,new BookOrder(3L,2L)
                ,new BookOrder(4L,2L)
        )
        ,List.of(
         new Book(1L,"qwe",2L,1000L,List.of(1L))
        ,new Book(2L,"qwe",2L,2000L,List.of(2L))
        ,new Book(3L,"qwe",4L,3000L,List.of(3L))
        )
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2000",
            "3,6000",
    })
    @DisplayName("책 가격에 있는 책의  북Id별 값이 나온다.")
    void test(Long bookId,Long expect) {
       Assertions.assertThat(bookDiscountPrice.getBookPrice(bookId)).isEqualTo(new Price(expect));
    }

    @Test
    @DisplayName("책주문이 책개수보다 많으면 예외반환")
    void test4() {
        Assertions.assertThatThrownBy(()->bookDiscountPrice =new BookDiscountPrice(List.of(
                new BookOrder(1L,2L)
                ,new BookOrder(3L,2L)
                ,new BookOrder(4L,2L)
        )
                ,List.of(
                new Book(1L,"qwe",1L,1000L,List.of(1L))
                ,new Book(2L,"qwe",2L,2000L,List.of(2L))
                ,new Book(3L,"qwe",4L,3000L,List.of(3L))
        )
        )).isInstanceOf(CustomException.class);
    }


    @ParameterizedTest
    @CsvSource(value = {
            "3",
            "4",
            "200",
            "300"
    })
    @DisplayName("책오더나 책중 하나만 있으면 0이 나온다.")
    void test2(Long bookId) {
        bookDiscountPrice =new BookDiscountPrice(List.of(
                new BookOrder(1L,2L)
                ,new BookOrder(3L,2L)
                ,new BookOrder(4L,2L)
        )
                ,List.of(
                new Book(1L,"qwe",2L,1000L,List.of(1L))
                ,new Book(200L,"qwe",2L,2000L,List.of(2L))
                ,new Book(300L,"qwe",4L,3000L,List.of(3L))
        )
        );
        Assertions.assertThat(bookDiscountPrice.getBookPrice(bookId)).isEqualTo(new Price(0L));
    }



    @Test
    @DisplayName("책가격이 있는 책의 총값이 나온다.")
    void test1() {
        Assertions.assertThat(bookDiscountPrice.getTotalBookPrice()).isEqualTo(new Price(8000L));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "8000,2,0",
            "8000,4,0",
            "8000,1,2000",
            "8000,3,6000",
            "16000,1,4000",
            "16000,3,12000"
    })
    @DisplayName("각책의 할인가격이 나온다.")
    void  test10(Long discountValue,Long bookId,Long expect) {
        Price price = new Price(discountValue);
        Assertions.assertThat(bookDiscountPrice.getPercentBookPrice(price,bookId)).isEqualTo(new Price(expect));
    }
}