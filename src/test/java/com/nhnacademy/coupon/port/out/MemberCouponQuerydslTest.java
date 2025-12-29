package com.nhnacademy.coupon.port.out;


import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.CouponDiscountType;
import com.nhnacademy.coupon.infra.RepositoryConfig;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@Import(RepositoryConfig.class)
@DataJpaTest
class MemberCouponQuerydslTest {
    private MemberCouponQuerydsl couponQueryDsl;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private CouponJpaRepository couponJpaRepository;
    @Autowired
    private MemberCouponJpaRepository memberCouponJpaRepository;
    @Autowired
    private CouponPolicyJpaRepository couponPolicyJpaRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        couponQueryDsl=new MemberCouponQuerydsl(jpaQueryFactory);
    }
    @Test
    @DisplayName("비어있음")
    void findAll() {
        Assertions.assertThat(couponQueryDsl.getCoupons(1L, PageRequest
                .of(1,10))).isEmpty();
    }
    @Test
    @DisplayName("값이 있다면 값 리스트를 반환")
    void findAll1() {
        couponPolicyJpaRepository.save(new CouponPolicyJpaEntity("생일",1000D,null,null, CouponDiscountType.FIXED_AMOUNT));
        couponPolicyJpaRepository.save(new CouponPolicyJpaEntity("10%할인",10D,null,null, CouponDiscountType.RATE));

        couponJpaRepository.save(new CouponJpaEntity(
                new Coupon(null, "qwe1", 1L, null, LocalDateTime.now().minusSeconds(1), LocalDateTime.now())));
        couponJpaRepository.save(new CouponJpaEntity(new Coupon(null,"qwe2",2L,null, LocalDateTime.now().minusSeconds(1), LocalDateTime.now())));

        memberCouponJpaRepository.save(new MemberCouponJpaEntity(1L,1L));


        Assertions.assertThat(couponQueryDsl.getCoupons(1L, PageRequest
                .of(0,10))).hasSize(1);
    }


}