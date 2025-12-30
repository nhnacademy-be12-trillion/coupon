package com.nhnacademy.coupon.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.NativeWebRequest;

class PageArgumentResolverTest {

    private PageArgumentResolver resolver;
    private NativeWebRequest webRequest;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        resolver = new PageArgumentResolver();
        webRequest = mock(NativeWebRequest.class);
        httpServletRequest = mock(HttpServletRequest.class);

        // webRequest.getNativeRequest 호출 시 mock 객체인 httpServletRequest를 반환하도록 설정
        when(webRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(httpServletRequest);
    }

    @Test
    @DisplayName("Pageable 타입의 파라미터를 지원하는지 확인한다")
    void supportsParameterTest() {
        // MethodParameter 추출을 위한 가짜 메서드 생성 (테스트용)
        MethodParameter pageableParam = getMethodParameter(Pageable.class);
        MethodParameter stringParam = getMethodParameter(String.class);

        assertThat(resolver.supportsParameter(pageableParam)).isTrue();
        assertThat(resolver.supportsParameter(stringParam)).isFalse();
    }

    @Test
    @DisplayName("요청 파라미터가 있을 때 올바른 PageRequest 객체를 생성한다")
    void resolveArgumentWithParams() throws Exception {
        // given
        when(httpServletRequest.getParameter("page")).thenReturn("2");
        when(httpServletRequest.getParameter("size")).thenReturn("20");

        // when
        PageRequest result = (PageRequest) resolver.resolveArgument(null, null, webRequest, null);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPageNumber()).isEqualTo(2);
        assertThat(result.getPageSize()).isEqualTo(20);
    }

    @Test
    @DisplayName("요청 파라미터가 없을 때 기본값(0, 10)을 반환한다")
    void resolveArgumentWithDefault() throws Exception {
        // given
        when(httpServletRequest.getParameter("page")).thenReturn(null);
        when(httpServletRequest.getParameter("size")).thenReturn(null);

        // when
        PageRequest result = (PageRequest) resolver.resolveArgument(null, null, webRequest, null);

        // then
        assertThat(result.getPageNumber()).isEqualTo(0);
        assertThat(result.getPageSize()).isEqualTo(10);
    }

    // 테스트를 돕기 위한 헬퍼 메서드
    private MethodParameter getMethodParameter(Class<?> parameterType) {
        try {
            // 임의의 메서드를 참조하여 MethodParameter 객체 생성
            return new MethodParameter(
                    TestController.class.getMethod("testMethod", parameterType), 0
            );
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    // MethodParameter 추출을 위한 더미 컨트롤러
    private static class TestController {
        public void testMethod(Pageable pageable) {}
        public void testMethod(String string) {}
    }
}