package com.nhnacademy.coupon.port.in.admin.coupon.maker;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackageClasses = CategoryCouponRequestMaker.class)
public class TestMakeRequestConfig {
}
