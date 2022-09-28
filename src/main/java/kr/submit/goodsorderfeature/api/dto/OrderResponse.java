package kr.submit.goodsorderfeature.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "주문아이디", example = "1")
    private final Long orderId;

    @Schema(description = "주문상태", example = "PROCESS")
    private final OrderStatus orderStatus;

    @Schema(description = "총가격", example = "25435000")
    private final long totalPrice;

    @Schema(description = "배달상태", example = "READY")
    private final DeliveryStatus deliveryStatus;

    @Schema(description = "주소", example = "서울시 강남구")
    private final String address;

    @Schema(description = "상세주소", example = "테헤란로 324길")
    private final String addressDetail;

    @Schema(description = "우편번호", example = "03453")
    private final String zipCode;

    @Schema(description = "상품목록")
    private final List<OrderGoodsSummary> orderGoods;

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
                .orderGoods(orderEntity.getOrderGoodsSummaries())
                .build();
    }
}