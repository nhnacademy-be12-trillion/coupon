package com.nhnacademy.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.CouponPolicyJpaRepository;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
class CouponServiceTest {

    // 가짜 데이터 정의
    private static final Long COUPON_ID = 1L;
    private static final Long POLICY_ID = 10L;
    private static final Long MEMBER_ID = 100L;
    private static final Long COUPON_QUANTITY = 5L;
    private static final Long USING_COUNT = 2L;
    @InjectMocks
    private CouponService couponService;
    @Mock
    private CouponJpaRepository couponJpaRepository;
    @Mock
    private CouponPolicyJpaRepository couponPolicyJpaRepository;
    @Mock
    private MakerComposite makerComposite;
    @Mock
    private MemberCouponJpaRepository memberCouponJpaRepository;
    private Coupon coupon;
    private CouponJpaEntity couponJpaEntity;
    private MemberCouponJpaEntity memberCouponJpaEntity;
    private Book book;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        coupon = new Coupon(COUPON_ID, "qwe",POLICY_ID,COUPON_QUANTITY,LocalDateTime.now(),LocalDateTime.now().plusDays(1));
        // 만료일이 현재보다 미래인 유효한 쿠폰 엔티티 설정
        couponJpaEntity = new CouponJpaEntity(coupon);
        memberCouponJpaEntity = new MemberCouponJpaEntity(MEMBER_ID,coupon.getId());
        book = new Book("123","123");
        pageable = PageRequest.of(0, 10);
    }

    // --- findAll Test ---
    @Test
    @DisplayName("findAll: 쿠폰 목록을 페이지네이션하여 반환하고 makerComposite를 사용해야 한다")
    void findAll_shouldReturnCouponsAndUseMakerComposite() {
        // given
        Page<CouponJpaEntity> entityPage = new PageImpl<>(List.of(couponJpaEntity), pageable, 1);
        when(couponJpaRepository.findAll(pageable)).thenReturn(entityPage);
        when(makerComposite.makeCoupon(any(CouponJpaEntity.class))).thenReturn(coupon);

        // when
        Collection<Coupon> actual = couponService.findAll(pageable);

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.iterator().next().getId()).isEqualTo(COUPON_ID);

        verify(couponJpaRepository, times(1)).findAll(pageable);
        verify(makerComposite, times(1)).makeCoupon(any(CouponJpaEntity.class));
    }

    // --- save Test ---
    @Test
    @DisplayName("save: 정책이 존재하면 쿠폰을 저장해야 한다")
    void save_shouldSaveCoupon_whenPolicyExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(true);

        // when
        couponService.save(coupon);

        // then
        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(makerComposite, times(1)).save(coupon);
    }

    @Test
    @DisplayName("save: 정책이 존재하지 않으면 CustomException을 발생시켜야 한다")
    void save_shouldThrowException_whenPolicyNotExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(false);

        // when, then
        assertThrows(CustomException.class, () -> couponService.save(coupon));

        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(makerComposite, never()).save(any(Coupon.class));
    }

    // --- update Test ---
    @Test
    @DisplayName("update: 정책과 쿠폰이 모두 존재하면 쿠폰을 업데이트해야 한다")
    void update_shouldUpdateCoupon_whenPolicyAndCouponExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(true);
        when(couponJpaRepository.existsById(COUPON_ID)).thenReturn(true);

        // when
        couponService.update(coupon);

        // then
        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(couponJpaRepository, times(1)).existsById(COUPON_ID);
        verify(makerComposite, times(1)).save(coupon);
    }

    @Test
    @DisplayName("update: 정책이 존재하지 않으면 CustomException을 발생시켜야 한다")
    void update_shouldThrowException_whenPolicyNotExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(false);

        // when, then
        assertThrows(CustomException.class, () -> couponService.update(coupon));

        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(couponJpaRepository, never()).existsById(anyLong());
        verify(makerComposite, never()).save(any(Coupon.class));
    }

    @Test
    @DisplayName("update: 쿠폰 ID가 존재하지 않으면 CustomException을 발생시켜야 한다")
    void update_shouldThrowException_whenCouponNotExists() {
        // given
        when(couponPolicyJpaRepository.existsById(POLICY_ID)).thenReturn(true);
        when(couponJpaRepository.existsById(COUPON_ID)).thenReturn(false);

        // when, then
        assertThrows(CustomException.class, () -> couponService.update(coupon));

        verify(couponPolicyJpaRepository, times(1)).existsById(POLICY_ID);
        verify(couponJpaRepository, times(1)).existsById(COUPON_ID);
        verify(makerComposite, never()).save(any(Coupon.class));
    }

    // --- useCoupon Test ---
    @Test
    @DisplayName("useCoupon: 사용 가능한 쿠폰이면 멤버 쿠폰을 사용 처리해야 한다")
    void useCoupon_shouldUseCoupon_whenAvailable() {
        // given
        // usingCount < quantity (5 - 2 = 3 > 0) 이고, isAvailable=true
        when(memberCouponJpaRepository.findByUsingCouponIdWithLock(COUPON_ID)).thenReturn(USING_COUNT);
        when(couponJpaRepository.findById(COUPON_ID)).thenReturn(Optional.of(couponJpaEntity));
        when(memberCouponJpaRepository.findByCouponIdAndMemberId(COUPON_ID, MEMBER_ID)).thenReturn(Optional.of(memberCouponJpaEntity));

        // when
        couponService.useCoupon(COUPON_ID, MEMBER_ID, book);

        // then
        verify(memberCouponJpaRepository, times(1)).findByUsingCouponIdWithLock(COUPON_ID);
        verify(couponJpaRepository, times(1)).findById(COUPON_ID);
        verify(memberCouponJpaRepository, times(1)).findByCouponIdAndMemberId(COUPON_ID, MEMBER_ID);
        Assertions.assertThat(memberCouponJpaEntity.isUse()).isTrue();
    }

    @Test
    @DisplayName("useCoupon: 쿠폰 ID가 존재하지 않으면 CustomException을 발생시켜야 한다")
    void useCoupon_shouldThrowException_whenCouponIdNotFound() {
        // given
        when(memberCouponJpaRepository.findByUsingCouponIdWithLock(COUPON_ID)).thenReturn(USING_COUNT);
        when(couponJpaRepository.findById(COUPON_ID)).thenReturn(Optional.empty());

        // when, then
        assertThrows(CustomException.class, () -> couponService.useCoupon(COUPON_ID, MEMBER_ID, book));

        verify(memberCouponJpaRepository, times(1)).findByUsingCouponIdWithLock(COUPON_ID);
        verify(couponJpaRepository, times(1)).findById(COUPON_ID);
        verify(memberCouponJpaRepository, never()).findByCouponIdAndMemberId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("useCoupon: 멤버 쿠폰이 존재하지 않으면 CustomException을 발생시켜야 한다")
    void useCoupon_shouldThrowException_whenMemberCouponNotFound() {
        // given
        when(memberCouponJpaRepository.findByUsingCouponIdWithLock(COUPON_ID)).thenReturn(USING_COUNT);
        when(couponJpaRepository.findById(COUPON_ID)).thenReturn(Optional.of(couponJpaEntity));
        // when, then
        assertThrows(CustomException.class, () -> couponService.useCoupon(COUPON_ID, MEMBER_ID, book));

    }

    @Test
    @DisplayName("useCoupon: 쿠폰 수량이 부족하면 CustomException을 발생시켜야 한다 (validateMemberCoupon)")
    void useCoupon_shouldThrowException_whenQuantityInsufficient() {
        // given
        // usingCount >= quantity (5 - 5 = 0) 이고, isAvailable=true
        when(memberCouponJpaRepository.findByUsingCouponIdWithLock(COUPON_ID)).thenReturn(COUPON_QUANTITY); // UsingCount = 5
        when(couponJpaRepository.findById(COUPON_ID)).thenReturn(Optional.of(couponJpaEntity));

        // when, then
        assertThrows(CustomException.class, () -> couponService.useCoupon(COUPON_ID, MEMBER_ID, book));

        verify(memberCouponJpaRepository, times(1)).findByUsingCouponIdWithLock(COUPON_ID);
        verify(couponJpaRepository, times(1)).findById(COUPON_ID);
    }

    // --- rollbackCoupon Test ---
    @Test
    @DisplayName("rollbackCoupon: 멤버 쿠폰이 존재하면 롤백 처리해야 한다")
    void rollbackCoupon_shouldRollbackCoupon_whenExists() {
        // given
        when(memberCouponJpaRepository.findByUsingCouponIdWithLock(COUPON_ID)).thenReturn(USING_COUNT);
        when(couponJpaRepository.findById(COUPON_ID)).thenReturn(Optional.of(couponJpaEntity));
        when(memberCouponJpaRepository.findByCouponIdAndMemberId(COUPON_ID, MEMBER_ID)).thenReturn(Optional.of(memberCouponJpaEntity));

         // when
        couponService.useCoupon(COUPON_ID, MEMBER_ID, book);
        couponService.rollbackCoupon(COUPON_ID, MEMBER_ID);

        Assertions.assertThat(memberCouponJpaEntity.isUse()).isFalse();
    }

    @Test
    @DisplayName("rollbackCoupon: 멤버 쿠폰이 존재하지 않으면 CustomException을 발생시켜야 한다")
    void rollbackCoupon_shouldThrowException_whenNotFound() {
        // given
        when(memberCouponJpaRepository.findByCouponIdAndMemberId(COUPON_ID, MEMBER_ID)).thenReturn(Optional.empty());

        // when, then
        assertThrows(CustomException.class, () -> couponService.rollbackCoupon(COUPON_ID, MEMBER_ID));

    }
}