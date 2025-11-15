package com.nhnacademy.coupon.port.in.membercoupon;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.service.MemberCoupon;
import com.nhnacademy.coupon.service.MemberCouponService;
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

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MemberCouponController.class)
class MemberCouponControllerTest {
    @MockitoBean
    private MemberCouponService service;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("아이디가 없으면 빈 리스트가 나온다.")
    void testFindAllByMemberId() throws Exception {
        Mockito.when(service.findAll(any(),any())).thenReturn(List.of());
        mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{memberId}/coupons",1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }
    @Test
    @DisplayName("아이디가 있으면 리스트가 나온다.")
    void testFindAllByMemberId2() throws Exception {
        Mockito.when(service.findAll(any(),any())).thenReturn(List.of(
                new MemberCoupon(1L,1L,1L,false, LocalDateTime.now())
        ));
        mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{memberId}/coupons",1L))
                .andExpect(status().isOk());
    }
}