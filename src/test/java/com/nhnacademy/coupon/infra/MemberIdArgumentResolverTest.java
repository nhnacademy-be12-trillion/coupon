package com.nhnacademy.coupon.infra;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.error.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@ExtendWith(MockitoExtension.class)
class MemberIdArgumentResolverTest {
    MemberIdArgumentResolver resolver;
    @Mock
    MethodParameter methodParameter;
    @Mock
    NativeWebRequest nativeWebRequest;
    @Mock
    HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        resolver = new MemberIdArgumentResolver();
    }
    @Test
    @DisplayName("MemberId 어노테이션가 없으면 지원되지않음.")
    void test1(){
        when(methodParameter.getParameterType()).thenReturn((Class) Component.class);
        Assertions.assertThat(resolver.supportsParameter(methodParameter)).isFalse();
    }
    @Test
    @DisplayName("MemberId 어노테이션가 있으면 지원함.")
    void test2(){
        when(methodParameter.getParameterType()).thenReturn((Class) MemberId.class);
        Assertions.assertThat(resolver.supportsParameter(methodParameter)).isTrue();
    }

    @Test
    @DisplayName("X-Member-Id가 없으면 실패한다")
    void test3(){
        when(nativeWebRequest.getNativeRequest(any())).thenReturn(httpServletRequest);

        Assertions.assertThatThrownBy(()->resolver.resolveArgument(methodParameter,null,nativeWebRequest,null)).isInstanceOf(
                CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(strings = {"qwe","qwe12"})
    @DisplayName("X-Member-Id가 숫자가 아니면 실패한다")
    void test3(String id){
        when(nativeWebRequest.getNativeRequest(any())).thenReturn(httpServletRequest);
        when(httpServletRequest.getHeader(any())).thenReturn(id);
        Assertions.assertThatThrownBy(()->resolver.resolveArgument(methodParameter,null,nativeWebRequest,null)).isInstanceOf(
                CustomException.class);
    }
    @ParameterizedTest
    @ValueSource(strings= {"1","2"})
    @DisplayName("X-Member-Id가 숫자면 성공한다")
    void test4(String id){
        when(nativeWebRequest.getNativeRequest(any())).thenReturn(httpServletRequest);
        when(httpServletRequest.getHeader(any())).thenReturn(id);
        Assertions.assertThatCode(()->resolver.resolveArgument(methodParameter,null,nativeWebRequest,null)).doesNotThrowAnyException();
    }


}