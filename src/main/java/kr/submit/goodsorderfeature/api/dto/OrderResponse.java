package kr.submit.goodsorderfeature.api.dto;

import kr.submit.goodsorderfeature.api.domain.code.OrderStatus;
import kr.submit.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderGoodsEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private List<OrderGoodsResponse> orderGoods;

    public static OrderResponse fromEntity(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .build();
    }
}