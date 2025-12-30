package com.nhnacademy.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.nhnacademy.coupon.domain.coupon.Book;
import com.nhnacademy.coupon.domain.coupon.BookOrder;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomNotFoundException;
import com.nhnacademy.coupon.port.out.BookClient;
import com.nhnacademy.coupon.port.out.BookResponse;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckCouponServiceTest {

    @InjectMocks
    private CheckCouponService checkCouponService;

    @Mock
    private MakerComposite makerComposite;

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @Mock
    private BookClient bookClient;

    @Test
    @DisplayName("쿠폰 사용 가능한 도서 목록을 필터링하여 반환한다.")
    void filterAvailableBook_Success() {
        // given
        Long couponId = 1L;
        List<BookOrder> bookOrders = List.of(new BookOrder(101L,2L), new BookOrder(102L,2L));

        // Mocking: Repository & Maker
        CouponJpaEntity mockCouponEntity = mock(CouponJpaEntity.class); // JPA 엔티티용
        Coupon domainCoupon = mock(Coupon.class); // 로직이 담긴 도메인 객체용
        given(couponJpaRepository.findById(couponId)).willReturn(Optional.of(mockCouponEntity));
        given(makerComposite.makeCoupon(any())).willReturn(domainCoupon);

        // Mocking: BookClient Response
        var bookResponse1 = new BookResponse(101L, "isbn-1", 10L, 15000L, List.of(1L));
        var bookResponse2 = new BookResponse(102L, "isbn-2", 5L, 20000L, List.of(2L));
        given(bookClient.getBooks(anyList())).willReturn(List.of(bookResponse1, bookResponse2));

        // Mocking: Coupon Filter Logic (101번 도서만 가능하다고 가정)
        given(domainCoupon.isAvailable(any(Book.class))).willAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            return book.getBookId().equals(101L);
        });

        // when
        List<Book> result = checkCouponService.filterAvailableBook(bookOrders, couponId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getBookId()).isEqualTo(101L);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 ID로 조회 시 예외가 발생한다.")
    void filterAvailableBook_CouponNotFound() {
        // given
        given(couponJpaRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> checkCouponService.filterAvailableBook(List.of(), 1L))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("error.message.notFoundCouponId");
    }
    @Test
    @DisplayName("도서 ID로 조회 시 도서 정보가 존재하면 Book 객체를 반환한다.")
    void filterAvailableBook_Single_Success() {
        // given
        Long bookId = 1L;
        // BookClient.BookResponse는 실제 프로젝트에 정의된 Record/Class 구조에 맞춰 생성하세요.
        var mockResponse = new BookResponse(
                bookId,
                "978-3-16-148410-0", // isbn
                100L,                 // stock
                15000L,               // price
                List.of(10L, 20L)    // categoryIds
        );

        given(bookClient.getBooks(List.of(bookId)))
                .willReturn(List.of(mockResponse));

        // when
        Book result = checkCouponService.filterAvailableBook(bookId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBookId()).isEqualTo(bookId);
        assertThat(result.getIsbn()).isEqualTo("978-3-16-148410-0");
        assertThat(result.getQuantity()).isEqualTo(100);
        assertThat(result.getCategoryIds()).contains(10L, 20L);
    }

    @Test
    @DisplayName("도서 ID로 조회했으나 결과가 없으면 CustomNotFoundException이 발생한다.")
    void filterAvailableBook_Single_ThrowsException() {
        // given
        Long bookId = 999L;
        // 빈 리스트 반환 설정
        given(bookClient.getBooks(anyList())).willReturn(List.of());

        // when & then
        assertThatThrownBy(() -> checkCouponService.filterAvailableBook(bookId))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("error.message.bookNotFound");
    }
}
