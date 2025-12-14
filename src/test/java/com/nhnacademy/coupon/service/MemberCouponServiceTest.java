package com.nhnacademy.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MemberCouponServiceTest {

    @Mock
    private MemberCouponJpaRepository memberCouponJpaRepository;

    @Mock
    private CouponJpaRepository couponJpaRepository;

    @InjectMocks
    private MemberCouponService memberCouponService;

    // 테스트에 필요한 Mock DTO/Entity 클래스 (실제 구현에 맞게 조정 필요)
    private MemberCoupon mockMemberCoupon;
    private MemberCouponJpaEntity mockMemberCouponJpaEntity;
    private Long testMemberId = 1L;
    private Long testCouponId = 100L;

    @BeforeEach
    void setUp() {
        // MemberCoupon DTO 초기화
        mockMemberCoupon = new MemberCoupon(
                1L,
                testMemberId,
                testCouponId,
                false,
                LocalDateTime.now()
        );

        // MemberCouponJpaEntity 초기화 (save 호출 시 필요)
        mockMemberCouponJpaEntity = new MemberCouponJpaEntity(
                testMemberId,
                testCouponId
        );
    }

    // --- findAll 테스트 ---

    @Test
    @DisplayName("회원 ID로 쿠폰 목록 조회 성공")
    void findAll_Success() {
        // Given
        Pageable pageable = Pageable.ofSize(10);

        MemberCouponJpaEntity entity1 = new MemberCouponJpaEntity(2L, testMemberId, 200L, false, LocalDateTime.now());
        MemberCouponJpaEntity entity2 = new MemberCouponJpaEntity(3L, testMemberId, 300L, true, LocalDateTime.now());

        List<MemberCouponJpaEntity> entities = List.of(entity1, entity2);

        when(memberCouponJpaRepository.findAllByMemberId(testMemberId, pageable))
                .thenReturn(entities);

        // When
        Collection<MemberCoupon> result = memberCouponService.findAll(testMemberId, pageable);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.stream().anyMatch(c -> c.getCouponId().equals(200L))).isTrue();
        assertThat(result.stream().anyMatch(c -> c.getCouponId().equals(300L))).isTrue();
        verify(memberCouponJpaRepository, times(1)).findAllByMemberId(testMemberId, pageable);
    }

    @Test
    @DisplayName("조회된 쿠폰이 없을 때 빈 목록 반환")
    void findAll_NoCouponsFound() {
        // Given
        Pageable pageable = Pageable.ofSize(10);

        when(memberCouponJpaRepository.findAllByMemberId(anyLong(), any(Pageable.class)))
                .thenReturn(List.of());

        // When
        Collection<MemberCoupon> result = memberCouponService.findAll(testMemberId, pageable);

        // Then
        assertThat(result).isEmpty();
        verify(memberCouponJpaRepository, times(1)).findAllByMemberId(testMemberId, pageable);
    }

    // --- save 테스트 ---

    @Test
    @DisplayName("쿠폰 저장(지급) 성공")
    void save_Success() {
        // Given
        // memberCoupon.getCouponId()에 해당하는 쿠폰이 존재함
        when(couponJpaRepository.existsById(testCouponId)).thenReturn(true);

        // When
        memberCouponService.save(mockMemberCoupon);

        // Then
        // 1. 쿠폰 존재 여부를 확인했는지 검증
        verify(couponJpaRepository, times(1)).existsById(testCouponId);
        // 2. MemberCouponJpaEntity 저장 로직이 호출되었는지 검증
        // ArgumentMatcher를 사용하여 memberId와 couponId가 올바르게 전달되었는지 확인하는 것이 이상적입니다.
        // 여기서는 any(MemberCouponJpaEntity.class)로 간단히 검증합니다.
        verify(memberCouponJpaRepository, times(1)).save(any(MemberCouponJpaEntity.class));
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 ID로 저장 시 CustomException 발생")
    void save_CouponNotFound_ThrowsException() {
        // Given
        // memberCoupon.getCouponId()에 해당하는 쿠폰이 존재하지 않음
        when(couponJpaRepository.existsById(testCouponId)).thenReturn(false);

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            memberCouponService.save(mockMemberCoupon);
        });

        // 예외 메시지 확인
        assertThat(exception.getMessage()).contains("error.message.notFoundCoupon");

        // 1. 쿠폰 존재 여부 확인은 호출되었는지 검증
        verify(couponJpaRepository, times(1)).existsById(testCouponId);
        // 2. MemberCouponJpaEntity 저장 로직은 호출되지 않았는지 검증
        verify(memberCouponJpaRepository, never()).save(any(MemberCouponJpaEntity.class));
    }
}