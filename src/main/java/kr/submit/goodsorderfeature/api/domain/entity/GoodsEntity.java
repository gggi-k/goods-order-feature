package kr.submit.goodsorderfeature.api.domain.entity;

import kr.submit.goodsorderfeature.core.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@SuperBuilder
@Entity
@Table(name = "GOODS")
@DynamicInsert
@DynamicUpdate
@Where(clause = "ENABLED = 'Y'")
@SQLDelete(sql = "UPDATE GOODS SET ENABLED = 'N' WHERE GOODS_ID = ? AND VERSION = ?")
public class GoodsEntity extends BaseEntity {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    @Column(name = "GOODS_ID")
    @Comment("상품아이디")
    private Long goodsId;

    @Column(name = "NAME", nullable = false)
    @Comment("상품명")
    private String name;

    @Column(name = "PRICE", nullable = false)
    @Comment("가격")
    private long price;

}