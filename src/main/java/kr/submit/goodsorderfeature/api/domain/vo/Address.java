package kr.submit.goodsorderfeature.api.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Address {

    private String address;
    private String addressDetail;
}