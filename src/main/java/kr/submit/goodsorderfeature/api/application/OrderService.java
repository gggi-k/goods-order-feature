package kr.submit.goodsorderfeature.api.application;

import kr.submit.goodsorderfeature.api.domain.entity.DeliveryEntity;
import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderGoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.submit.goodsorderfeature.api.dto.OrderRequest;
import kr.submit.goodsorderfeature.api.dto.OrderResponse;
import kr.submit.goodsorderfeature.api.event.OrderEvent;
import kr.submit.goodsorderfeature.api.repository.OrderRepository;
import kr.submit.goodsorderfeature.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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