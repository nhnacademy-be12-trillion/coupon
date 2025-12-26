package com.nhnacademy.coupon.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomNotFoundException extends CustomException {
    public CustomNotFoundException(String message, Object[] args) {
        super(message, HttpStatus.NOT_FOUND, args);
    }
    public CustomNotFoundException(String message) {
        this(message, null);
    }
}
