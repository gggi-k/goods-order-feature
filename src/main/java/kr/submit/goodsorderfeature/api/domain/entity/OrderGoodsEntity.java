package kr.submit.goodsorderfeature.api.domain.entity;

import kr.submit.goodsorderfeature.api.domain.code.OrderGoodsStatus;
import kr.submit.goodsorderfeature.api.domain.code.OrderStatus;
import kr.submit.goodsorderfeature.api.domain.vo.OrderGoodsSummary;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.submit.goodsorderfeature.core.error.ForbiddenException;
import kr.submit.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "ORDER_GOODS")
public class OrderGoodsEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_GOODS_ID")
    @Comment("주문상품아이디")
    private Long orderGoodsId;

    @Column(name = "GOODS_ID", updatable = false, nullable = false)
    @Comment("상품아이디")
    private Long goodsId;

    @Column(name = "NAME", updatable = false, nullable = false)
    @Comment("상품명")
    private String name;

    @Column(name = "COUNT", updatable = false, nullable = false)
    @ColumnDefault("'0'")
    @Comment("갯수")
    private int count;

    @Column(name = "PRICE", updatable = false, nullable = false)
    @ColumnDefault("'0'")
    @Comment("가격")
    private long price;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PROCESS'")
    @Column(name = "ORDER_GOODS_STATUS", updatable = false, nullable = false)
    @Comment("주문상품상태")
    private OrderGoodsStatus orderGoodsStatus = OrderGoodsStatus.PROCESS;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID", updatable = false, nullable = false)
    private OrderEntity order;

    public static OrderGoodsEntity fromOrderGoodsRequestForProcess(OrderGoodsRequest orderGoodsRequest) {
        return OrderGoodsEntity.builder()
                .goodsId(orderGoodsRequest.getGoodsId())
                .name(orderGoodsRequest.getName())
                .price(orderGoodsRequest.getPrice())
                .count(orderGoodsRequest.getCount())
                .orderGoodsStatus(OrderGoodsStatus.PROCESS)
                .build();
    }

    public static List<OrderGoodsEntity> fromOrderGoodsRequestsForProcess(List<OrderGoodsRequest> orderGoodsRequests) {
        return orderGoodsRequests.stream().map(OrderGoodsEntity::fromOrderGoodsRequestForProcess).collect(Collectors.toList());
    }

    public static OrderGoodsEntity fromOrderGoodsRequestForCancel(OrderGoodsRequest orderGoodsRequest) {
        return OrderGoodsEntity.builder()
                .goodsId(orderGoodsRequest.getGoodsId())
                .name(orderGoodsRequest.getName())
                .price(orderGoodsRequest.getPrice())
                .count(-orderGoodsRequest.getCount())
                .orderGoodsStatus(OrderGoodsStatus.CANCEL)
                .build();
    }

    public long getTotalPrice() {
        return this.getPrice() * this.getCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderGoodsEntity that = (OrderGoodsEntity) o;
        return orderGoodsId != null && Objects.equals(orderGoodsId, that.orderGoodsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderGoodsId);
    }
}