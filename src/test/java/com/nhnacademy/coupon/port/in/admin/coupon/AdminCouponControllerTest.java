package com.nhnacademy.coupon.port.in.admin.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.in.admin.coupon.maker.RequestMakerComposite;
import com.nhnacademy.coupon.service.CouponService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminCouponController.class)
class AdminCouponControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CouponService couponService;
    @MockitoBean
    private RequestMakerComposite requestMakerComposite;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("저장에서 문제 생기면, 400 반환")
    void test2() throws Exception {
        Mockito.doThrow(new CustomException("qwe")).when(couponService).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/admin/coupons"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("저장 되면, 200 반환")
    void test3() throws Exception {
        Mockito.doNothing().when(couponService).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/admin/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CouponRequestImpl("qwe", 1L, 20L, LocalDateTime.now()
                                , LocalDateTime.now().plusDays(1), "qwe", "qwe"))
                        )
                )
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("업데이트에서 문제 생기면, 400 반환")
    void test4() throws Exception {
        Mockito.doThrow(new CustomException("qwe")).when(couponService).update(any());
        mockMvc.perform(RestDocumentationRequestBuilders.put("/admin/coupons/1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("업데이트 되면, 200 반환")
    void test5() throws Exception {
        Mockito.doNothing().when(couponService).update(any());
        mockMvc.perform(RestDocumentationRequestBuilders.put("/admin/coupons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new CouponRequestImpl("qwe", 1L, 20L, LocalDateTime.now()
                                , LocalDateTime.now().plusDays(1), "qwe", "qwe"))
                ))
                .andExpect(status().isOk());
    }
}