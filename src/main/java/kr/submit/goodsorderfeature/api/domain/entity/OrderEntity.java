package kr.submit.goodsorderfeature.api.domain.entity;

import kr.submit.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.submit.goodsorderfeature.api.domain.code.OrderStatus;
import kr.submit.goodsorderfeature.api.domain.vo.Address;
import kr.submit.goodsorderfeature.core.error.ForbiddenException;
import kr.submit.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@SuperBuilder
@Entity
@Table(name = "ORDERS")
public class OrderEntity extends BaseEntity {

    @Transient
    private final Collection<Object> domainEvents = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    @Comment("주문아이디")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    @Comment("주문상태")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderGoodsEntity> orderGoods = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private DeliveryEntity delivery;

    public void addOrderGoods(List<OrderGoodsEntity> orderGoods) {
        this.orderGoods.addAll(orderGoods);

        for (OrderGoodsEntity orderGood : orderGoods) {
            orderGood.setOrder(this);
        }
    }

    public void changeDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
        delivery.setOrderEntity(this);
    }


    public void cancel() {
        if(this.delivery.isComplete()) throw new ForbiddenException("배송완료일시 취소가 불가능합니다");

        this.orderStatus = OrderStatus.CANCEL;
    }

    public long getTotalPrice() {
        return this.orderGoods.stream().mapToLong(OrderGoodsEntity::getTotalPrice)
                .sum();
    }


}