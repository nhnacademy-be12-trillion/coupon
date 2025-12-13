package com.nhnacademy.coupon.error;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    @ExceptionHandler(CustomException.class)
    ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.debug(e.getMessage());
        Locale locale = RequestContextUtils.getLocale(request);

        String message = messageSource.getMessage(e.getMessage(), e.getArgs(), locale);
        return ResponseEntity.status(e.getStatusCode())
                .body(new CustomErrorResponse(message,request.getRequestURI()));
    }
}
