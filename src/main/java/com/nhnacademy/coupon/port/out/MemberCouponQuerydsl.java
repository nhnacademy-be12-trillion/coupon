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
    public List<MemberCouponResponse> getCoupons(boolean isUse,Long memberId, Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        MemberCouponResponse.class,
                        QMemberCouponJpaEntity.memberCouponJpaEntity.id,
                        QCouponJpaEntity.couponJpaEntity.id,
                        QCouponJpaEntity.couponJpaEntity.name,
                        QCouponJpaEntity.couponJpaEntity.quantity,
                        QCouponJpaEntity.couponJpaEntity.issueStartDate,
                        QCouponJpaEntity.couponJpaEntity.issueEndDate,
                        QCouponPolicyJpaEntity.couponPolicyJpaEntity.discountValue,
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
                        QMemberCouponJpaEntity.memberCouponJpaEntity.memberId.eq(memberId)
                                .and(QMemberCouponJpaEntity.memberCouponJpaEntity.isUse.eq(isUse))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
