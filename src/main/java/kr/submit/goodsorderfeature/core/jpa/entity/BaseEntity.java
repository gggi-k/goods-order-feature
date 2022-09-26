package kr.submit.goodsorderfeature.core.jpa.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Builder.Default
    @Type(type = "yes_no")
    @ColumnDefault("'Y'")
    @Comment("활성여부")
    @Column(name = "ENABLED", updatable = false, nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Comment("등록일시")
    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @ColumnDefault("0")
    @Comment("등록자")
    @Column(name = "CREATED_BY", updatable = false, nullable = false)
    private Long createdBy;

    @LastModifiedDate
    @Comment("갱신일시")
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Comment("갱신자")
    @Column(name = "UPDATED_BY")
    private Long updatedBy;
}