package kr.project.goodsorderfeature.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.project.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.project.goodsorderfeature.api.domain.vo.Address;
import kr.project.goodsorderfeature.api.domain.entity.DeliveryEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class DeliveryResponse {

    @Schema(description = "배달아이디", example = "1")
    private final Long deliveryId;

    @Schema(description = "배달상태", example = "READY")
    private final DeliveryStatus deliveryStatus;

    @Schema(description = "주소", example = "서울시 강남구")
    private final String address;

    @Schema(description = "상세주소", example = "테헤란로 324길")
    private final String addressDetail;

    @Schema(description = "우편번호", example = "03453")
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