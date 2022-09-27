package kr.submit.goodsorderfeature.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class GoodsResponse {

    @Schema(description = "상품아이디", example = "245609095435")
    private final Long goodsId;
    @Schema(description = "상품명", example = "상품1")
    private final String name;
    @Schema(description = "가격", example = "1000")
    private final long price;

    public static GoodsResponse fromEntity(GoodsEntity goodsEntity) {
        return GoodsResponse.builder()
                .goodsId(goodsEntity.getGoodsId())
                .name(goodsEntity.getName())
                .price(goodsEntity.getPrice())
                .build();
    }

    public static List<GoodsResponse> fromEntities(List<GoodsEntity> goodsEntities) {
        return goodsEntities.stream().map(GoodsResponse::fromEntity).collect(Collectors.toList());
    }

    public static Page<GoodsResponse> fromEntitiesPage(Page<GoodsEntity> goodsEntityPage) {
        return goodsEntityPage.map(GoodsResponse::fromEntity);
    }
}