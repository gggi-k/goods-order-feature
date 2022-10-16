package kr.project.goodsorderfeature.api.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeliveryStatus {

    READY("배송 준비중"),
    COMPLETE("배송 완료");

    private final String name;
}