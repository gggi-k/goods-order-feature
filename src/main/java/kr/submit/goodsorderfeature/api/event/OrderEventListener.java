package kr.submit.goodsorderfeature.api.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {

    @Async
    @TransactionalEventListener(condition = "#orderEvent.isProcess()")
    public void handleOrderProcess(OrderEvent orderEvent) {

        System.out.println("주문접수 되었습니다");
    }

    @Async
    @TransactionalEventListener(condition = "#orderEvent.isCancel()")
    public void handleOrderCancel(OrderEvent orderEvent) {

        System.out.println("주문취소 되었습니다");
    }
}