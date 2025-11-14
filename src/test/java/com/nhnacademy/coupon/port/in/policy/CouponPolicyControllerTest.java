package com.nhnacademy.coupon.port.in.policy;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.service.CouponDisCountType;
import com.nhnacademy.coupon.service.CouponPolicy;
import com.nhnacademy.coupon.service.CouponPolicyService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = CouponPolicyController.class)
@AutoConfigureRestDocs
class CouponPolicyControllerTest {
    @MockitoBean
    private CouponPolicyService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("없으면 빈 리스트가 출력된다.")
    void test1() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/policies"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    @DisplayName("있으면 리스트가 출력된다.")
    void test2() throws Exception {
        Mockito.when(service.getCouponPolicys(any())).thenReturn(
                List.of(new CouponPolicy(1L,1d,100L,100L, CouponDisCountType.FIXED_AMOUNT))
        );
        mockMvc.perform(RestDocumentationRequestBuilders.get("/policies"))
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
    @Test
    @DisplayName("저장이 안되면, 400 반환")
    void test3() throws Exception {
        Mockito.doThrow(new CustomException("error.message.fixAmount")).when(service).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/policies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyCreateRequest(100D,200L,2000L,CouponDisCountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("coupon-policys/save/fail",
                        preprocessRequest(prettyPrint())
                ));
    }
    @Test
    @DisplayName("저장이 되면, 200대 반환")
    void test4() throws Exception {
        Mockito.doNothing().when(service).save(any());
        mockMvc.perform(RestDocumentationRequestBuilders.post("/policies")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyCreateRequest(100D,200L,2000L,CouponDisCountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isOk())
                .andDo(document("coupon-policys/save/success",
                        preprocessRequest(prettyPrint()))
                );
    }
    @Test
    @DisplayName("예외 나오면 400 반환")
    void test6() throws Exception {
        Mockito.doThrow(new CustomException("qwe")).when(service).update(any());
        mockMvc.perform(RestDocumentationRequestBuilders.put("/policies/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyUpdateRequest(100D,200L,2000L,CouponDisCountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isBadRequest())
                .andDo(document("coupon-policys/update/fail",
                        preprocessRequest(prettyPrint()))
                );
    }
    @Test
    @DisplayName("예외 안나오면 200 반환")
    void test5() throws Exception {
        Mockito.doNothing().when(service).update(any());
        mockMvc.perform(RestDocumentationRequestBuilders.put("/policies/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new PolicyUpdateRequest(100D,200L,2000L,CouponDisCountType.FIXED_AMOUNT)))
                )
                .andExpect(status().isOk())
                .andDo(document("coupon-policys/update/success",
                        preprocessRequest(prettyPrint()))
                );
    }
    @Test
    @DisplayName("삭제- 돌아간다.")
    void test7() throws Exception {
        Mockito.doNothing().when(service).delete(any());
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/policies/1")
                )
                .andExpect(status().isOk())
                .andDo(document("coupon-policys/delete/success",
                        preprocessRequest(prettyPrint()))
                );
    }
}