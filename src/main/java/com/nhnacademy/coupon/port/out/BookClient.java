package com.nhnacademy.coupon.port.out;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "book-service")
public interface BookClient {
    @GetMapping(value = "/books/categories",params = "bookIds")
    List<BookResponse> getBooks(@RequestParam List<Long> bookIds);

}
