package kr.submit.goodsorderfeature.api.domain.vo;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class OrderGoodsId implements Serializable {

    private static final long serialVersionUID = 1687914895937112510L;

    @Column(name = "ORDER_ID")
    @Comment("주문아이디")
    private Long orderId;

    @Column(name = "GOODS_ID")
    @Comment("상품아이디")
    private String goodsId;
}