package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.domain.coupon.BookIdCoupon;
import com.nhnacademy.coupon.domain.coupon.CategoryIdCoupon;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.infra.RepositoryConfig;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
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
    @Autowired
    private EntityManager em;
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
    @Test
    @DisplayName("도서 ID 또는 카테고리 ID가 일치하는 쿠폰 목록을 조회한다.")
    void findCouponBook_Success() {
        // given
        Long targetBookId = 100L;
        Set<Long> targetCategoryIds = Set.of(1L, 2L);

        // 1. 도서 ID가 일치하는 쿠폰
        CouponJpaEntity bookCoupon = new CouponJpaEntity(
                new BookIdCoupon(null,"도서할인쿠폰",1L,null,LocalDateTime.now().minusSeconds(1),LocalDateTime.now(),targetBookId)
        );

        // 2. 카테고리 ID가 일치하는 쿠폰
        CouponJpaEntity categoryCoupon =  new CouponJpaEntity(
                new CategoryIdCoupon(null,"카테고리할인쿠폰",1L,null,LocalDateTime.now().minusSeconds(1),LocalDateTime.now(),1L)
        );
        CouponJpaEntity categoryCoupon1 =  new CouponJpaEntity(
                new CategoryIdCoupon(null,"카테고리할인쿠폰1",1L,null,LocalDateTime.now().minusSeconds(1),LocalDateTime.now(),3L)
        );

        CouponJpaEntity coupon =  new CouponJpaEntity(
                new Coupon(null,"쿠폰",1L,null,LocalDateTime.now().minusSeconds(1),LocalDateTime.now())
        );


        em.persist(bookCoupon);
        em.persist(categoryCoupon);
        em.persist(categoryCoupon1);
        em.persist(coupon);
        em.flush();

        // when
        List<CouponJpaEntity> result = couponQueryDsl.findCouponBook(targetBookId, targetCategoryIds);

        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).extracting("name")
                .containsExactlyInAnyOrder("도서할인쿠폰", "카테고리할인쿠폰");
    }
}