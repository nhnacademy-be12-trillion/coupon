package com.nhnacademy.coupon.infra;

import com.nhnacademy.coupon.error.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdArgumentResolver implements CustomArgumentResolver {

    public static final String X_MEMBER_ID = "X-Member-Id";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                            NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory)
            throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request.getHeader(X_MEMBER_ID)==null){
            throw new CustomException("error.message.notFoundMemberId", HttpStatus.UNAUTHORIZED);
        }
        try{
            return Long.parseLong(request.getHeader(X_MEMBER_ID));
        }catch(NumberFormatException e){
            throw new CustomException("error.message.notFoundMemberId", HttpStatus.UNAUTHORIZED);
        }
    }
}
