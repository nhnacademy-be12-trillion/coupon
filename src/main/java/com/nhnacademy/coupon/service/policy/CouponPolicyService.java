package com.nhnacademy.coupon.service.policy;

import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.CouponDiscountTypeColumn;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {
    private final CouponPolicyJpaRepository couponPolicyJpaRepository;

    public List<CouponPolicy> getCouponPolicys(Pageable pageable) {
        return couponPolicyJpaRepository.findAll(pageable)
                .stream()
                .map(this::create)
                .toList();
    }
    @Transactional
    public void save(CouponPolicy couponPolicy) {
        couponPolicyJpaRepository.save(createCouponPolicyJpaEntity(couponPolicy));
    }
    @Transactional
    public void update(CouponPolicy couponPolicy) {
        if(!couponPolicyJpaRepository.existsById(couponPolicy.id()))
            throw new CustomException("error.message.couponPolicy.notFound",new Object[]{couponPolicy.id()});
        couponPolicyJpaRepository.save(createCouponPolicyJpaEntity(couponPolicy));
    }
    @Transactional
    public void delete(Long policyId) {
        couponPolicyJpaRepository.deleteById(policyId);
    }
    CouponPolicy create(CouponPolicyJpaEntity entity) {
        return new CouponPolicy(entity.getId(), entity.getDiscountValue(), entity.getMinOrderPrice(), entity.getMaxDiscountPrice(),
                getCouponDisCountType(entity.getDiscountType()));
    }
    CouponPolicyJpaEntity createCouponPolicyJpaEntity(CouponPolicy couponPolicy) {
        return new CouponPolicyJpaEntity(couponPolicy.discountValue(),couponPolicy.minOrderPrice(),couponPolicy.maxDiscountPrice(),getCouponDisCountTypeColumn(couponPolicy.couponDiscountType()));
    }
    CouponDisCountType getCouponDisCountType(CouponDiscountTypeColumn column) {
        return switch(column) {
            case CouponDiscountTypeColumn.FIX_AMOUNT-> CouponDisCountType.FIXED_AMOUNT;
            case CouponDiscountTypeColumn.RATE-> CouponDisCountType.RATE;
        };
    }
    CouponDiscountTypeColumn getCouponDisCountTypeColumn(CouponDisCountType discountType) {
        return switch (discountType){
            case FIXED_AMOUNT -> CouponDiscountTypeColumn.FIX_AMOUNT;
            case RATE -> CouponDiscountTypeColumn.RATE;
        };
    }
}
