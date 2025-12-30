package com.nhnacademy.coupon.port.in.membercoupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.port.out.MemberCouponQuerydsl;
import com.nhnacademy.coupon.port.out.MemberCouponResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(MemberCouponQueryController.class)
class MemberCouponQueryControllerTest {
    public static final String PATH = "/member-coupons";
    public static final String X_MEMBER_ID = "X-Member-Id";
    @MockitoBean
    private MemberCouponQuerydsl service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("아이디가 없으면 빈 리스트가 나온다.")
    void testFindAllByMemberId() throws Exception {
        Mockito.when(service.getCoupons(eq(false),any(),any())).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .header(X_MEMBER_ID,1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }
    @Test
    @DisplayName("아이디가 있으면 리스트가 나온다.")
    void testFindAllByMemberId2() throws Exception {
        Mockito.when(service.getCoupons(eq(false),any(),any())).thenReturn(List.of(
                new MemberCouponResponse(1L,1L,"qwe",1L, LocalDateTime.now(), LocalDateTime.now(),null,null,null,
                        CouponDiscountType.FIXED_AMOUNT)
        ));
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .header(X_MEMBER_ID,1L))
                .andExpect(status().isOk());
    }

}