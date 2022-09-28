package kr.submit.goodsorderfeature.api.domain.entity;

import kr.submit.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.submit.goodsorderfeature.api.domain.vo.Address;
import kr.submit.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@SuperBuilder
@Entity
@Table(name = "DELIVERY")
public class DeliveryEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long deliveryId;

    @Column(name = "DELIVERY_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @AttributeOverrides({
        @AttributeOverride(name = "address", column = @Column(name = "ADDRESS")),
        @AttributeOverride(name = "addressDetail", column = @Column(name = "ADDRESS_DETAIL")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE"))
    })
    @Embedded
    private Address address;

    @Setter
    @OneToOne(mappedBy = "delivery")
    private OrderEntity orderEntity;

    public static DeliveryEntity ofAddressForReady(Address address) {
        return DeliveryEntity.builder()
                .address(address)
                .deliveryStatus(DeliveryStatus.READY)
                .build();
    }

    public boolean isComplete() {
        return DeliveryStatus.COMPLETE == this.deliveryStatus;
    }

    public void changeComplete() {
        this.deliveryStatus = DeliveryStatus.COMPLETE;
    }

}