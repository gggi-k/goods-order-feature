package kr.submit.goodsorderfeature.api.domain.vo;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Embeddable;

@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class Address {

    private String address;
    private String addressDetail;
    private String zipCode;
}