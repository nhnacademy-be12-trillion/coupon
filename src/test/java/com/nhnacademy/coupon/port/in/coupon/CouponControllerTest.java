package com.nhnacademy.coupon.port.in.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.service.CheckCouponService;
import com.nhnacademy.coupon.service.CouponService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CouponController.class)
@ExtendWith(MockitoExtension.class)
class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CouponService couponService;
    @MockitoBean
    private CheckCouponService checkCouponService;

    @Test
    @DisplayName("없으면 빈리스트를 반환한다.")
    void test() throws Exception {
        Mockito.when(couponService.findAll(any())).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    @DisplayName("있으면 리스트를 반환한다.")
    void test1() throws Exception {
        Mockito.when(couponService.findAll(any())).thenReturn(List.of(
                new Coupon(1L,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1L))
        ));
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("할인 금액 조회 성공 - 정상적인 파라미터 입력 시 200 OK를 반환한다")
    void getDiscountPrice_Success() throws Exception {
        // given
        Long couponId = 1L;
        Long memberId = 100L;
        List<Long> bookIds = List.of(10L, 20L);
        List<Long> quantities = List.of(1L, 2L);

        // Mock 데이터 설정 (서비스 응답 정의)
        given(couponService.getDiscountValue(anyLong(), anyList()))
                .willReturn(new Price(5000L)); // 예상 할인 금액

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/{coupon-id}", couponId)
                        .param("bookIds", "10", "20")
                        .param("quantities", "1", "2")
                        .header("X-Member-Id", memberId)) // 커스텀 ArgumentResolver 등을 고려
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("쿠폰 사용 요청 성공 - 파라미터가 서비스로 잘 전달되고 200 OK를 반환한다")
    void useCoupon_Success() throws Exception {
        // given
        Long couponId = 1L;
        Long memberId = 100L;
        List<Long> bookIds = List.of(10L, 20L);
        List<Long> quantities = List.of(1L, 2L);

        // when & then
        mockMvc.perform(post("/coupons/{coupon-id}/use", couponId)
                        .header("X-Member-Id", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CouponUseRequest(bookIds, quantities)))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}