package kr.submit.goodsorderfeature.api.event;

import kr.submit.goodsorderfeature.api.domain.code.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEvent {

    private final OrderStatus orderStatus;

    public static OrderEvent createProcess() {
        return new OrderEvent(OrderStatus.PROCESS);
    }

    public static OrderEvent createCancel() {
        return new OrderEvent(OrderStatus.CANCEL);
    }

    public boolean isProcess() {
        return OrderStatus.PROCESS == this.orderStatus;
    }

    public boolean isCancel() {
        return OrderStatus.CANCEL == this.orderStatus;
    }
}