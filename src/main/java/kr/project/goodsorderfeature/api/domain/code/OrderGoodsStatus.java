package kr.project.goodsorderfeature.api.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderGoodsStatus {

    PROCESS("주문 접수"),
    CANCEL("주문 취소");

    private final String name;
}