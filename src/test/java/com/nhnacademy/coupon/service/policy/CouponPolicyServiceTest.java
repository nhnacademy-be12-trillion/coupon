package com.nhnacademy.coupon.service.policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.domain.policy.AllPricePolicy;
import com.nhnacademy.coupon.domain.policy.CouponDisCountType;
import com.nhnacademy.coupon.domain.policy.CouponPolicy;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.CouponDiscountTypeColumn;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaEntity;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CouponPolicyServiceTest {

    // 테스트에 필요한 가짜 데이터 준비 (가정)
    // 실제 프로젝트에서는 record나 class가 필요합니다.
    private static final Long POLICY_ID = 1L;
    private static final Double DISCOUNT_VALUE = 10D;
    private static final Long MIN_ORDER_PRICE = 5000L;
    private static final Long MAX_DISCOUNT_PRICE = 10000L;
    private static final CouponDisCountType DISCOUNT_TYPE_DOMAIN = CouponDisCountType.RATE;
    private static final CouponDiscountTypeColumn DISCOUNT_TYPE_COLUMN = CouponDiscountTypeColumn.RATE;
    @InjectMocks
    private CouponPolicyService couponPolicyService;
    @Mock
    private CouponPolicyJpaRepository couponPolicyJpaRepository;
    private CouponPolicy couponPolicy;
    private CouponPolicyJpaEntity couponPolicyJpaEntity;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        // Mock 데이터 설정
        couponPolicy = new AllPricePolicy(POLICY_ID, MIN_ORDER_PRICE, MAX_DISCOUNT_PRICE, DISCOUNT_VALUE,DISCOUNT_TYPE_DOMAIN);
        couponPolicyJpaEntity = new CouponPolicyJpaEntity(POLICY_ID, DISCOUNT_VALUE, MIN_ORDER_PRICE, MAX_DISCOUNT_PRICE, DISCOUNT_TYPE_COLUMN);
        pageable = PageRequest.of(0, 10);
    }

    // --- getCouponPolicys Test ---
    @Test
    @DisplayName("getCouponPolicys는 정책 목록을 페이지네이션하여 반환해야 한다")
    void getCouponPolicys_shouldReturnPolicyList() {
        // given
        Page<CouponPolicyJpaEntity> entityPage = new PageImpl<>(List.of(couponPolicyJpaEntity), pageable, 1);
        when(couponPolicyJpaRepository.findAll(pageable)).thenReturn(entityPage);

        // when
        List<CouponPolicy> actual = couponPolicyService.getCouponPolicys(pageable);

        // then
        assertThat(actual).hasSize(1);
        CouponPolicy result = actual.get(0);
        assertThat(result.getId()).isEqualTo(POLICY_ID);
        assertThat(result.getDiscountValue()).isEqualTo(DISCOUNT_VALUE);
        assertThat(result.getCouponDiscountType()).isEqualTo(DISCOUNT_TYPE_DOMAIN);

        verify(couponPolicyJpaRepository, times(1)).findAll(pageable);
    }

    // --- save Test ---
    @Test
    @DisplayName("save는 CouponPolicy를 JpaEntity로 변환하여 저장해야 한다")
    void save_shouldSaveCouponPolicy() {
        // given
        // when
        couponPolicyService.save(couponPolicy);

        // then
        // JpaEntity로 변환된 객체를 저장했는지 검증
        verify(couponPolicyJpaRepository, times(1)).save(any(CouponPolicyJpaEntity.class));
    }

    // --- update Test ---
    @Test
    @DisplayName("update는 정책이 존재하면 CouponPolicy를 업데이트해야 한다")
    void update_shouldUpdateCouponPolicy_whenExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(true);

        // when
        couponPolicyService.update(couponPolicy);

        // then
        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(couponPolicyJpaRepository, times(1)).save(any(CouponPolicyJpaEntity.class));
    }

    @Test
    @DisplayName("update는 정책이 존재하지 않으면 CustomException을 발생시켜야 한다")
    void update_shouldThrowException_whenNotExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(false);

        // when, then
        assertThrows(CustomException.class, () -> couponPolicyService.update(couponPolicy));

        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(couponPolicyJpaRepository, never()).save(any(CouponPolicyJpaEntity.class)); // save 호출 안 됨 확인
    }

    // --- delete Test ---
    @Test
    @DisplayName("delete는 policyId로 정책을 삭제해야 한다")
    void delete_shouldDeletePolicyById() {
        // given
        // when
        couponPolicyService.delete(POLICY_ID);

        // then
        verify(couponPolicyJpaRepository, times(1)).deleteById(POLICY_ID);
    }

}