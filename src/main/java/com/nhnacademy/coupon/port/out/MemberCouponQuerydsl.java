package com.nhnacademy.coupon.port.out;

import com.nhnacademy.coupon.port.out.coupon.QCouponJpaEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponQuerydsl {
    private final JPAQueryFactory jpaQueryFactory;
    public List<MemberCouponResponse> getCoupons(Long memberId, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        MemberCouponResponse.class,
                        QMemberCouponJpaEntity.memberCouponJpaEntity.id,
                        QCouponJpaEntity.couponJpaEntity.name,
                        QCouponJpaEntity.couponJpaEntity.quantity,
                        QCouponJpaEntity.couponJpaEntity.issueStartDate,
                        QCouponJpaEntity.couponJpaEntity.issueEndDate,
                        QCouponPolicyJpaEntity.couponPolicyJpaEntity.minOrderPrice,
                        QCouponPolicyJpaEntity.couponPolicyJpaEntity.maxDiscountPrice,
                        QCouponPolicyJpaEntity.couponPolicyJpaEntity.discountType
                ))
                .from(QMemberCouponJpaEntity.memberCouponJpaEntity)
                .join(QCouponJpaEntity.couponJpaEntity)
                .on(QMemberCouponJpaEntity.memberCouponJpaEntity.couponId.eq(QCouponJpaEntity.couponJpaEntity.id))
                .join(QCouponPolicyJpaEntity.couponPolicyJpaEntity)
                .on(QCouponJpaEntity.couponJpaEntity.policyId.eq(QCouponPolicyJpaEntity.couponPolicyJpaEntity.id))
                .where(
                        QMemberCouponJpaEntity.memberCouponJpaEntity.id.eq(memberId)
                                .and(QMemberCouponJpaEntity.memberCouponJpaEntity.isUse.isFalse())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
