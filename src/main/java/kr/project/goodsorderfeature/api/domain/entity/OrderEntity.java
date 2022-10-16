package kr.project.goodsorderfeature.api.domain.entity;

import kr.project.goodsorderfeature.api.domain.code.DeliveryStatus;
import kr.project.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.project.goodsorderfeature.core.error.ForbiddenException;
import kr.project.goodsorderfeature.api.domain.code.OrderStatus;
import kr.project.goodsorderfeature.api.domain.vo.OrderGoodsSummary;
import kr.project.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "ORDERS")
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    @Comment("주문아이디")
    private Long orderId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    @Comment("주문상태")
    private OrderStatus orderStatus = OrderStatus.PROCESS;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<OrderGoodsEntity> orderGoods = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "DELIVERY_ID")
    private DeliveryEntity delivery;


    public static OrderEntity ofDeliveryAndOrderGoods(DeliveryEntity delivery, List<OrderGoodsEntity> orderGoods) {
        OrderEntity orderEntity = OrderEntity.builder()
                .orderStatus(OrderStatus.PROCESS)
                .build();

        orderEntity.changeDelivery(delivery);
        orderEntity.addOrderGoods(orderGoods);

        return orderEntity;
    }

    private void addOrderGoods(List<OrderGoodsEntity> orderGoods) {
        for (OrderGoodsEntity orderGood : orderGoods) {
            orderGood.setOrder(this);
        }

        this.orderGoods.addAll(orderGoods);
    }

    public void changeDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
        delivery.setOrderEntity(this);
    }


    public void cancel(List<OrderGoodsRequest> orderGoodsRequests) {
        if(this.delivery.isComplete()) throw new ForbiddenException("배송완료일시 취소가 불가능합니다");

        final Map<Long, OrderGoodsSummary> orderGoodsSummariesForMap = this.getOrderGoodsSummariesForMap();
        final List<OrderGoodsEntity> list = new LinkedList<>();

        for (OrderGoodsRequest orderGoodsRequest : orderGoodsRequests) {
            Optional<OrderGoodsSummary> orderGoodsSummaryOptional = Optional.ofNullable(orderGoodsSummariesForMap.get(orderGoodsRequest.getGoodsId()));
            if(orderGoodsSummaryOptional.isEmpty()) throw new ForbiddenException("취소할려는 상품이 존재하지 않습니다");

            OrderGoodsSummary orderGoodsSummary = orderGoodsSummaryOptional.get();
            if(orderGoodsSummary.isNotCountOverZero(orderGoodsRequest.getCount())) throw new ForbiddenException("구매한 상품갯수보다 취소하는 상품갯수가 더많습니다");

            list.add(OrderGoodsEntity.fromOrderGoodsRequestForCancel(orderGoodsRequest
                    .setName(orderGoodsSummary.getName())
                    .setPrice(orderGoodsSummary.getPrice())));
        }

        this.addOrderGoods(list);

        if(this.isGoodsAllCancel()) this.orderStatus = OrderStatus.CANCEL;
    }

    public List<OrderGoodsSummary> getOrderGoodsSummaries() {
        return new ArrayList<>(this.getOrderGoodsSummariesForMap().values());
    }

    private Map<Long, OrderGoodsSummary> getOrderGoodsSummariesForMap() {
        final Map<Long, OrderGoodsSummary> map = new HashMap<>();

        for (OrderGoodsEntity orderGood : this.orderGoods) {
            final Long goodsId = orderGood.getGoodsId();
            final Optional<OrderGoodsSummary> orderGoodsSummary = Optional.ofNullable(map.get(goodsId));

            if(orderGoodsSummary.isPresent()) {
                map.put(goodsId, orderGoodsSummary.get().addCount(orderGood.getCount()));
            } else {
                map.put(goodsId, OrderGoodsSummary.of(orderGood.getGoodsId(), orderGood.getName(), orderGood.getCount(), orderGood.getPrice()));
            }
        }

        return map;
    }

    private boolean isGoodsAllCancel() {
        return this.getOrderGoodsSummariesForMap().values().stream().allMatch(orderGoodsSummary -> orderGoodsSummary.getCount() == 0);
    }

    public long getTotalPrice() {
        return this.orderGoods.stream().mapToLong(OrderGoodsEntity::getTotalPrice)
                .sum();
    }

    public void complete() {
        if(DeliveryStatus.COMPLETE != this.delivery.getDeliveryStatus()) throw new ForbiddenException("배달완료된 상태일때 주문완료가 가능합니다");
        this.orderStatus = OrderStatus.COMPLETE;
    }

}