package kr.submit.goodsorderfeature.api.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import javax.persistence.Id;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class GoodsEntity {

    @Id
    private String goodsId;
}