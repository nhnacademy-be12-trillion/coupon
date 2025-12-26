package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.infra.RepositoryConfig;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@Import(RepositoryConfig.class)
@DataJpaTest
class CouponQueryDslTest {
    private CouponQueryDsl couponQueryDsl;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @BeforeEach
    void setUp(){
        couponQueryDsl=new CouponQueryDsl(jpaQueryFactory);
    }

    @Test
    @DisplayName("북id와 장바구니가 없으면 빈값이 반환한다.")
    void save() {
        couponJpaRepository.save(new CouponJpaEntity(null,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1),null,null));
        Assertions.assertThat(couponQueryDsl.findCouponBook(2L, Set.of(), PageRequest.of(0,5))).isEmpty();
    }
    @Test
    @DisplayName("아이디가 널인걸 저장하면 값이 정상적으로 작동한다.")
    void save1() {
        couponJpaRepository.save(new CouponJpaEntity(null,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1),1L,null));
        couponJpaRepository.save(new CouponJpaEntity(null,"qwe",1L,1L, LocalDateTime.now(),LocalDateTime.now().plusDays(1),null,1L));

        Assertions.assertThat(couponQueryDsl.findCouponBook(1L, Set.of(1L), PageRequest.of(
                0,5
        ))).hasSize(2);
    }
}