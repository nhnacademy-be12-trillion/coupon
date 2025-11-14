package com.nhnacademy.coupon.service.policy;

import com.nhnacademy.coupon.error.CustomException;
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
                .map(CouponPolicy::create)
                .toList();
    }
    @Transactional
    public void save(CouponPolicy couponPolicy) {
        couponPolicyJpaRepository.save(couponPolicy.createCouponPolicyJpaEntity());
    }
    @Transactional
    public void update(CouponPolicy couponPolicy) {
        if(!couponPolicyJpaRepository.existsById(couponPolicy.id()))
            throw new CustomException("error.message.couponPolicy.notFound",new Object[]{couponPolicy.id()});
        couponPolicyJpaRepository.save(couponPolicy.createCouponPolicyJpaEntity());
    }
    @Transactional
    public void delete(Long policyId) {
        couponPolicyJpaRepository.deleteById(policyId);
    }
}
