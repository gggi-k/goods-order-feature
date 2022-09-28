package kr.submit.goodsorderfeature.api.dto;

import kr.submit.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.submit.goodsorderfeature.api.domain.code.OrderStatus;
import kr.submit.goodsorderfeature.api.domain.entity.DeliveryEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderGoodsEntity;
import kr.submit.goodsorderfeature.api.domain.vo.Address;
import kr.submit.goodsorderfeature.api.domain.vo.OrderGoodsSummary;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class OrderResponse {

    private final Long orderId;
    private final OrderStatus orderStatus;
    private final List<OrderGoodsSummary> orderGoods;
    private final long totalPrice;

    private final DeliveryStatus deliveryStatus;
    private final String address;
    private final String addressDetail;
    private final String zipCode;

    public static OrderResponse fromEntity(OrderEntity orderEntity) {
        final DeliveryEntity delivery = orderEntity.getDelivery();
        final Address address = delivery.getAddress();

        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .orderStatus(orderEntity.getOrderStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .deliveryStatus(delivery.getDeliveryStatus())
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .build();
    }
}