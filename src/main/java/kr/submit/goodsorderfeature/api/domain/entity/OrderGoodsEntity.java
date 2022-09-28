package kr.submit.goodsorderfeature.api.domain.entity;

import kr.submit.goodsorderfeature.api.domain.vo.OrderGoodsId;
import kr.submit.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@Entity
@Table(name = "ORDER_GOODS")
public class OrderGoodsEntity extends BaseEntity {

    @EmbeddedId
    private OrderGoodsId orderGoodsId;

    @Column(name = "NAME")
    @Comment("상품명")
    private String name;

    @Column(name = "COUNT", nullable = false)
    @ColumnDefault("'0'")
    @Comment("갯수")
    private int count;

    @Column(name = "PRICE", nullable = false)
    @ColumnDefault("'0'")
    @Comment("가격")
    private long price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID", insertable = false, updatable = false, nullable = false)
    private OrderEntity order;

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