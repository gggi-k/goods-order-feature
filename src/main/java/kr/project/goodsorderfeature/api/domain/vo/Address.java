package kr.project.goodsorderfeature.api.domain.vo;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Comment;

import javax.persistence.Embeddable;

@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class Address {

    @Comment("주소")
    private String address;

    @Comment("상세주소")
    private String addressDetail;

    @Comment("우편번호")
    private String zipCode;
}