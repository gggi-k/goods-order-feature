package kr.submit.goodsorderfeature.api.dto;


import kr.submit.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.submit.goodsorderfeature.api.domain.entity.DeliveryEntity;
import kr.submit.goodsorderfeature.api.domain.vo.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class DeliveryResponse {

    private final Long deliveryId;
    private final DeliveryStatus deliveryStatus;
    private final String address;
    private final String addressDetail;
    private final String zipCode;

    public static DeliveryResponse fromEntity(DeliveryEntity deliveryEntity) {
        final Address address = deliveryEntity.getAddress();

        return DeliveryResponse.builder()
                .deliveryId(deliveryEntity.getDeliveryId())
                .deliveryStatus(deliveryEntity.getDeliveryStatus())
                .address(address.getAddress())
                .addressDetail(address.getAddressDetail())
                .zipCode(address.getZipCode())
                .build();
    }
}