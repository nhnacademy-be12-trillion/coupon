package com.nhnacademy.coupon.port.in.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CouponController.class)
@ExtendWith(MockitoExtension.class)
class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CouponService couponService;

    @Test
    @DisplayName("없으면 빈리스트를 반환한다.")
    void test() throws Exception {
        Mockito.when(couponService.findAll(any())).thenReturn(List.of());
        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    @DisplayName("있으면 리스트를 반환한다.")
    void test1() throws Exception {
        Mockito.when(couponService.findAll(any())).thenReturn(List.of(
                new Coupon(1L,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1L))
        ));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupons"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("저장에서 문제 생기면, 400 반환")
    void test2() throws Exception {
        Mockito.doThrow(new CustomException("qwe")).when(couponService).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/coupons"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("저장 되면, 200 반환")
    void test3() throws Exception {
        Mockito.doNothing().when(couponService).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/coupons"))
                .andExpect(status().isBadRequest());
    }
}