package com.nhnacademy.coupon.port.out;

import java.util.List;

public record BookResponse (Long bookId, String isbn, Long bookStock, Long bookSalePrice, List<Long> categoryIds){
}
