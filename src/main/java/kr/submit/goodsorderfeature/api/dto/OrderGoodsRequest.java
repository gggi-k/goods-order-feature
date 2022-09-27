package kr.submit.goodsorderfeature.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Setter
@Getter
@ToString
public class OrderGoodsRequest {

    private Long orderGoodsId;

    private int count;
}