package com.nhnacademy.coupon.service.policy;

import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import com.nhnacademy.coupon.service.CouponPolicyComposite;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {
    private static final String WELCOME = "WELCOME";
    private final CouponPolicyJpaRepository couponPolicyJpaRepository;

    public List<CouponPolicy> getCouponPolicys(Pageable pageable) {
        return couponPolicyJpaRepository.findAll(pageable)
                .stream()
                .map(this::create)
                .toList();
    }

    public void validateExistById(Long id){
        if(!couponPolicyJpaRepository.existsById(id))
            throw new CustomException("error.message.couponPolicy.notFound",new Object[]{id});
    }

    @Transactional
    public void save(CouponPolicy couponPolicy) {
        couponPolicyJpaRepository.save(createCouponPolicyJpaEntity(couponPolicy));
    }
    @Transactional
    public void update(CouponPolicy couponPolicy) {
        validateExistById(couponPolicy.getId());
        couponPolicyJpaRepository.save(createCouponPolicyJpaEntity(couponPolicy));
    }
    @Transactional
    public void delete(Long policyId) {
        couponPolicyJpaRepository.deleteById(policyId);
    }
    private CouponPolicy create(CouponPolicyJpaEntity entity) {
        return CouponPolicyComposite.couponPolicy(entity.getId(), entity.getName(),entity.getDiscountValue(), entity.getMinOrderPrice(), entity.getMaxDiscountPrice(),entity.getDiscountType());
    }
    private CouponPolicyJpaEntity createCouponPolicyJpaEntity(CouponPolicy couponPolicy) {
        return new CouponPolicyJpaEntity(couponPolicy.getId(),couponPolicy.getName(),couponPolicy.getDiscountValue(),couponPolicy.getMinOrderPrice(),couponPolicy.getMaxDiscountPrice(),couponPolicy.getCouponDiscountType());
    }

    public void validatePolicy(Long policyId, Price price) {
        CouponPolicy couponPolicy = create(couponPolicyJpaRepository.findById(policyId)
                .orElseThrow(() -> new CustomException("error.message.couponPolicy.notFound", new Object[]{policyId}))
        );
        couponPolicy.getApplyCouponPrice(price);
    }

    public CouponPolicy getWelcomePolicy() {
        return create(couponPolicyJpaRepository.findByName(WELCOME).get());
    }
}
