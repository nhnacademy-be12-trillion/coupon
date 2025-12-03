package com.nhnacademy.coupon.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Getter
public class CustomException extends RuntimeException implements ErrorResponse {
    private final HttpStatus status;
    private final Object[] args;

    public CustomException(String message,HttpStatus status,Object[] args) {
        super(message);
        this.status = status;
        this.args = args;
    }
    public CustomException(String message) {
        this(message,HttpStatus.BAD_REQUEST,null);
    }
    public CustomException(String message,Object[] args) {
        this(message,HttpStatus.BAD_REQUEST,args);
    }

    public CustomException(String message,HttpStatus status) {
        this(message,status,null);
    }
    public CustomException(Throwable cause,HttpStatus status,Object[] args) {
        super(cause);
        this.status = status;
        this.args = args;
    }

    public CustomException(Throwable cause) {
        this(cause,HttpStatus.INTERNAL_SERVER_ERROR,null);
    }
    public CustomException(Throwable cause,HttpStatus status) {
        this(cause,status,null);
    }
    public CustomException(Throwable cause,Object[] args) {
        this(cause,HttpStatus.INTERNAL_SERVER_ERROR,args);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return status;
    }

    @Override
    public ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(status, getMessage());
    }
}
