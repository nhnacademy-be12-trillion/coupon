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
}
