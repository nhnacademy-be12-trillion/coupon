package com.nhnacademy.coupon.port.out;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@EntityListeners(AuditingEntityListener.class)
public class MemberCouponJpaEntity {
    @Id
    private Long id;
    private Long memberId;
    private Long couponId;
    private boolean use;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public MemberCouponJpaEntity(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
    }

    public void useCoupon() {
        use=true;
    }

    public void rollback() {
        use=false;
    }
}
