package kr.project.goodsorderfeature.api.application;

import kr.project.goodsorderfeature.api.dto.OrderRequest;
import kr.project.goodsorderfeature.api.event.OrderEvent;
import kr.project.goodsorderfeature.api.repository.OrderRepository;
import kr.project.goodsorderfeature.core.error.NotFoundException;
import kr.project.goodsorderfeature.api.domain.entity.DeliveryEntity;
import kr.project.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.project.goodsorderfeature.api.domain.entity.OrderGoodsEntity;
import kr.project.goodsorderfeature.api.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final GoodsService goodsService;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public OrderResponse findByOrderId(Long orderId) {
        return OrderResponse.fromEntity(orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다")));
    }

    public OrderResponse create(OrderRequest orderRequest) {

        final DeliveryEntity delivery = DeliveryEntity.ofAddressForReady(orderRequest.getAddress());
        final List<OrderGoodsEntity> orderGoods = OrderGoodsEntity.fromOrderGoodsRequestsForProcess(orderRequest.getOrderGoodsIds());

        eventPublisher.publishEvent(OrderEvent.createProcess());

        return OrderResponse.fromEntity(orderRepository.save(OrderEntity.ofDeliveryAndOrderGoods(delivery, orderGoods)));
    }

    public OrderResponse cancel(OrderRequest orderRequest) {
        final OrderEntity orderEntity = orderRepository.findById(orderRequest.getOrderId()).orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));

        orderEntity.cancel(orderRequest.getOrderGoodsIds());

        eventPublisher.publishEvent(OrderEvent.createCancel());
        return OrderResponse.fromEntity(orderRepository.save(orderEntity));
    }
}