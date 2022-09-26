package kr.submit.goodsorderfeature.api.dto;

import kr.submit.goodsorderfeature.api.domain.GoodsEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class GoodsResponse {

    private final String goodsId;

    public static GoodsResponse fromEntity(GoodsEntity goodsEntity) {
        return GoodsResponse.builder()
                .goodsId(goodsEntity.getGoodsId())
                .build();
    }
}