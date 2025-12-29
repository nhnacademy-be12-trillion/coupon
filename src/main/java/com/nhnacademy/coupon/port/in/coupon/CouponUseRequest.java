package com.nhnacademy.coupon.port.in.coupon;

import java.util.List;

record CouponUseRequest(List<Long> bookIds, List<Long> quantities) {
}
