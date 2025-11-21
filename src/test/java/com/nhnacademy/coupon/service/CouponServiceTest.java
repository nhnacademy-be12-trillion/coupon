package com.nhnacademy.coupon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.coupon.domain.Book;
import com.nhnacademy.coupon.domain.coupon.Coupon;
import com.nhnacademy.coupon.domain.policy.Price;
import com.nhnacademy.coupon.error.CustomException;
import com.nhnacademy.coupon.port.out.MemberCouponJpaEntity;
import com.nhnacademy.coupon.port.out.MemberCouponJpaRepository;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaEntity;
import com.nhnacademy.coupon.port.out.coupon.CouponJpaRepository;
import com.nhnacademy.coupon.service.maker.MakerComposite;
import com.nhnacademy.coupon.service.policy.CouponPolicyService;
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

    @Mock
    private CouponJpaRepository couponJpaRepository;

    private final Long TEST_COUPON_ID = 1L;

    @Mock
    private MakerComposite makerComposite;

    @Mock
    private MemberCouponJpaRepository memberCouponJpaRepository;
    private final Long TEST_MEMBER_ID = 100L;
    @Mock
    private CouponPolicyService couponPolicyService;
    @InjectMocks
    private CouponService couponService;
    private Coupon mockCoupon;
    private CouponJpaEntity mockCouponEntity;
    private MemberCouponJpaEntity mockMemberCouponEntity;
    private Book mockBook;

    @BeforeEach
    void setUp() {
        mockCoupon = mock(Coupon.class);
        mockCouponEntity = mock(CouponJpaEntity.class);
        mockMemberCouponEntity = mock(MemberCouponJpaEntity.class);

        mockBook = new Book(10000L,"ㅂㅈㄷ","ㅂㅈㄷ");
    }

    @Test
    @DisplayName("findAll_성공_쿠폰목록반환")
    void findAll_success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<CouponJpaEntity> entityList = List.of(mockCouponEntity);
        Page<CouponJpaEntity> page = new PageImpl<>(entityList, pageable, 1);

        given(couponJpaRepository.findAll(pageable)).willReturn(page);
        given(makerComposite.makeCoupon(any(CouponJpaEntity.class))).willReturn(mockCoupon);

        Collection<Coupon> result = couponService.findAll(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(couponJpaRepository, times(1)).findAll(pageable);
        verify(makerComposite, times(1)).makeCoupon(mockCouponEntity);
    }

    @Test
    @DisplayName("save_성공_쿠폰저장")
    void save_success() {
        doNothing().when(couponPolicyService).validateExistById(any());
        when(couponJpaRepository.save(any())).thenReturn(mockCouponEntity);

        Assertions.assertThatCode(()->couponService.save(mockCoupon)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("save_실패_정책없음")
    void save_fail_policyNotFound() {
        doThrow(new CustomException("")).when(couponPolicyService).validateExistById(any());

        assertThrows(CustomException.class, () -> couponService.save(mockCoupon));


    }

    @Test
    @DisplayName("update_성공_쿠폰업데이트")
    void update_success() {
        doNothing().when(couponPolicyService).validateExistById(any());
        given(couponJpaRepository.existsById(any())).willReturn(true);
        given(makerComposite.makeCouponEntity(mockCoupon)).willReturn(mockCouponEntity);
        given(couponJpaRepository.save(mockCouponEntity)).willReturn(mockCouponEntity);

        Assertions.assertThatCode(()->couponService.update(mockCoupon)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("update_실패_쿠폰없음")
    void update_fail_couponNotFound() {
        doNothing().when(couponPolicyService).validateExistById(any());
        given(couponJpaRepository.existsById(any())).willReturn(false);

        assertThrows(CustomException.class, () -> couponService.update(mockCoupon));
    }

    @Test
    @DisplayName("useCoupon_성공_쿠폰사용")
    void useCoupon_success() {
        given(couponJpaRepository.findById(any())).willReturn(Optional.of(mockCouponEntity));
        given(makerComposite.makeCoupon(mockCouponEntity)).willReturn(mockCoupon);
        given(memberCouponJpaRepository.findByUsingCouponIdWithLock(any())).willReturn(0L);
        doNothing().when(mockCoupon).validateCoupon(any(Book.class), anyLong());
        doNothing().when(couponPolicyService).validatePolicy(anyLong(), any(Price.class));
        given(memberCouponJpaRepository.findByCouponIdAndMemberId(any(), anyLong()))
                .willReturn(Optional.of(mockMemberCouponEntity));

        Assertions.assertThatCode(()->couponService.useCoupon(TEST_COUPON_ID, TEST_MEMBER_ID, mockBook));
    }

    @Test
    @DisplayName("useCoupon_실패_쿠폰없음")
    void useCoupon_fail_couponNotFound() {
        given(couponJpaRepository.findById(TEST_COUPON_ID)).willReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> couponService.useCoupon(TEST_COUPON_ID, TEST_MEMBER_ID, mockBook));

        verify(memberCouponJpaRepository, never()).findByCouponIdAndMemberId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("useCoupon_실패_멤버쿠폰없음")
    void useCoupon_fail_memberCouponNotFound() {
        given(couponJpaRepository.findById(TEST_COUPON_ID)).willReturn(Optional.of(mockCouponEntity));
        given(makerComposite.makeCoupon(mockCouponEntity)).willReturn(mockCoupon);
        given(memberCouponJpaRepository.findByUsingCouponIdWithLock(TEST_COUPON_ID)).willReturn(0L);
        given(memberCouponJpaRepository.findByCouponIdAndMemberId(TEST_COUPON_ID, TEST_MEMBER_ID))
                .willReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> couponService.useCoupon(TEST_COUPON_ID, TEST_MEMBER_ID, mockBook));

        verify(mockMemberCouponEntity, never()).useCoupon();
    }

    @Test
    @DisplayName("rollbackCoupon_성공_쿠폰롤백")
    void rollbackCoupon_success() {
        given(memberCouponJpaRepository.findByCouponIdAndMemberId(TEST_COUPON_ID, TEST_MEMBER_ID))
                .willReturn(Optional.of(mockMemberCouponEntity));

        couponService.rollbackCoupon(TEST_COUPON_ID, TEST_MEMBER_ID);

        verify(memberCouponJpaRepository, times(1)).findByCouponIdAndMemberId(TEST_COUPON_ID, TEST_MEMBER_ID);
        verify(mockMemberCouponEntity, times(1)).rollback();
    }

    @Test
    @DisplayName("rollbackCoupon_실패_멤버쿠폰없음")
    void rollbackCoupon_fail_memberCouponNotFound() {
        given(memberCouponJpaRepository.findByCouponIdAndMemberId(TEST_COUPON_ID, TEST_MEMBER_ID))
                .willReturn(Optional.empty());

        assertThrows(CustomException.class,
                () -> couponService.rollbackCoupon(TEST_COUPON_ID, TEST_MEMBER_ID));

        verify(mockMemberCouponEntity, never()).rollback();
    }
}