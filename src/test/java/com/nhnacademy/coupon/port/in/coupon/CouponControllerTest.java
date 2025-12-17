package com.nhnacademy.coupon.port.in.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.TestCouponPolicy;
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
    @DisplayName("정책이 있으면 총판매가격을 알려준다.")
    void test2() throws Exception {
        Mockito.when(couponService.getCouponPolicy(1L)).thenReturn(new TestCouponPolicy());
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/1")
                        .header("X-Member-Id",1L)
                        .param("price",String.valueOf(TestCouponPolicy.MIN_ORDER_PRICE+10_000)))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("할인이 안되면 400 반환.")
    void test3() throws Exception {
        Mockito.when(couponService.getCouponPolicy(1L)).thenReturn(new TestCouponPolicy());
        mockMvc.perform(MockMvcRequestBuilders.get("/coupons/1")
                        .header("X-Member-Id",1L)
                        .param("price",String.valueOf(TestCouponPolicy.MIN_ORDER_PRICE-100)))
                .andExpect(status().isBadRequest());
    }

}