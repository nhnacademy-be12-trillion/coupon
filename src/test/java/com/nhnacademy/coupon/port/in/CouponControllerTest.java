package com.nhnacademy.coupon.port.in;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.coupon.service.CouponDisCountType;
import com.nhnacademy.coupon.service.CouponPolicy;
import com.nhnacademy.coupon.service.CouponService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = CouponController.class)
@AutoConfigureRestDocs
class CouponControllerTest {
    @MockitoBean
    private CouponService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("없으면 빈 리스트가 출력된다.")
    void test1() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon-policys"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    @DisplayName("있으면 리스트가 출력된다.")
    void test2() throws Exception {
        Mockito.when(service.getCouponPolicys(any())).thenReturn(
                List.of(new CouponPolicy(1L,1d,100L,100L, CouponDisCountType.FIXED_AMOUNT))
        );
        mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon-policys"))
                .andExpect(status().isOk())
                .andDo(document("coupon-policys/not-empty"
                                , responseFields(
                                        fieldWithPath("[]").description("쿠폰 정책들"),
                                fieldWithPath("[].id").description("쿠폰정책 id"),
                                fieldWithPath("[].discountValue").description("할인값/할인 타입이 FIXED_AMOUNT이면 금액할인/RATE이면 할인율"),
                                fieldWithPath("[].couponDiscountType").description("FIXED_AMOUNT이면 금액할인/RATE이면 할인율"),
                                fieldWithPath("[].minOrderPrice").description("최소 주문금액"),
                                fieldWithPath("[].maxDiscountPrice").description("최대 할인금액")
                                ))
                );
    }
}