package com.nhnacademy.coupon.port.in.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.service.BookCouponService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookCouponService couponService;

    @Test
    @DisplayName("도서 ID로 쿠폰 목록 조회 성공")
    void getBookCoupons_Success() throws Exception {
        // given
        Long bookId = 1L;
        // Coupon 도메인 객체의 생성자나 필드 상황에 맞게 수정이 필요할 수 있습니다.
        Coupon mockCoupon = new Coupon(1L,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusSeconds(1)); // 실제 Coupon 클래스 구조에 맞춰 인스턴스화 하세요.
        List<Coupon> coupons = List.of(mockCoupon);

        given(couponService.getCoupons(eq(bookId), any(Pageable.class)))
                .willReturn(coupons);

        // when & then
        mockMvc.perform(get("/book-coupons/{book-id}", bookId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}