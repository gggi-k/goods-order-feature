package kr.submit.goodsorderfeature.api.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Builder
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    private String orderId;
}