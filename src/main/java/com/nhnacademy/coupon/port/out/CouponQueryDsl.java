package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.QCouponJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    public List<CouponJpaEntity> findCouponBook(Long bookId, Set<Long> categoryIds, Pageable pageable) {
        return jpaQueryFactory.selectFrom(
                        QCouponJpaEntity.couponJpaEntity
                )
                .where(
                        QCouponJpaEntity.couponJpaEntity.bookId.eq(bookId)
                                .or(QCouponJpaEntity.couponJpaEntity.categoryId.in(categoryIds))
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    public List<CouponJpaEntity> findCouponBook(Long memberId,Long bookId, Set<Long> categoryIds) {
        QCouponJpaEntity coupon = QCouponJpaEntity.couponJpaEntity;
        QMemberCouponJpaEntity memberCoupon = QMemberCouponJpaEntity.memberCouponJpaEntity;

        return jpaQueryFactory.selectFrom(coupon)
                // 멤버 쿠폰 테이블과 Left Join (쿠폰ID가 같고, 해당 멤버의 것인 데이터만)
                .leftJoin(memberCoupon).on(
                        memberCoupon.couponId.eq(coupon.id),
                        memberCoupon.memberId.eq(memberId)
                )
                .where(
                        // 기존 조건
                        (coupon.bookId.eq(bookId).or(coupon.categoryId.in(categoryIds))),

                        // Join 결과가 null이면 해당 멤버가 이 쿠폰을 가지고 있지 않다는 뜻
                        memberCoupon.id.isNull()
                )
                .fetch();
    }
}
