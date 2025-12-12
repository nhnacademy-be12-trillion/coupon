package com.nhnacademy.coupon.port.in.admin.policy;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.domain.policy.AllPricePolicy;
import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.service.policy.CouponPolicyService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CouponPolicyController.class)
class CouponPolicyControllerTest {
    public static final String PATH = "/admin/coupon-policies";
    @MockitoBean
    private CouponPolicyService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("없으면 빈 리스트가 출력된다.")
    void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    @DisplayName("있으면 리스트가 출력된다.")
    void test2() throws Exception {
        Mockito.when(service.getCouponPolicys(any())).thenReturn(
                List.of(new AllPricePolicy(1L,"qwe",100L,1L,100D, CouponDiscountType.FIXED_AMOUNT))
        );
        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("저장이 안되면, 400 반환")
    void test3() throws Exception {
        Mockito.doThrow(new CustomException("error.message.fixAmount")).when(service).save(any());
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyCreateRequest("qwe",100D,200L,2000L,
                                CouponDiscountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("저장이 되면, 200대 반환")
    void test4() throws Exception {
        Mockito.doNothing().when(service).save(any());
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyCreateRequest("q23",100D,200L,2000L,
                                CouponDiscountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("예외 나오면 400 반환")
    void test6() throws Exception {
        Mockito.doThrow(new CustomException("qwe")).when(service).update(any());
        mockMvc.perform(put("/admin/policies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PolicyUpdateRequest("qwe",100D,200L,2000L,
                                CouponDiscountType.FIXED_AMOUNT)))
                );
    }
    @Test
    @DisplayName("예외 안나오면 200 반환")
    void test5() throws Exception {
        Mockito.doNothing().when(service).update(any());
        mockMvc.perform(put(PATH+"/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyUpdateRequest("qwe",100D,200L,2000L,
                                CouponDiscountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("삭제- 돌아간다.")
    void test7() throws Exception {
        Mockito.doNothing().when(service).delete(any());
        mockMvc.perform(delete(PATH+"/1"))
                .andExpect(status().isOk());
    }
}