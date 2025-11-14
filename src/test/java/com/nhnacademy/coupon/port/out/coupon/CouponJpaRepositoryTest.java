package com.nhnacademy.coupon.port.out.coupon;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@DataJpaTest
class CouponJpaRepositoryTest {
    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Test
    @DisplayName("아이디가 널인걸 저장하면 값이 정상적으로 작동한다.")
    void save() {
        Assertions.assertThatCode(()->couponJpaRepository.save(new CouponJpaEntity(null,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1)))).doesNotThrowAnyException();
        Assertions.assertThat(couponJpaRepository.findAll().getFirst().getId()).isEqualTo(1);
    }
}