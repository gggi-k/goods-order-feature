package kr.submit.goodsorderfeature.api.application;

import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.submit.goodsorderfeature.api.domain.entity.OrderGoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.submit.goodsorderfeature.api.dto.OrderRequest;
import kr.submit.goodsorderfeature.api.dto.OrderResponse;
import kr.submit.goodsorderfeature.api.repository.OrderRepository;
import kr.submit.goodsorderfeature.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional(readOnly = true)
    public OrderResponse findByOrderId(Long orderId) {
        return OrderResponse.fromEntity(orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다")));
    }

    public OrderResponse create(OrderRequest orderRequest) {

        List<OrderGoodsRequest> goodsIds = orderRequest.getOrderGoodsIds();
        final List<GoodsResponse> goodsResponses = goodsService.findAllByGoodsIds(goodsIds.stream().map(OrderGoodsRequest::getOrderGoodsId)
                .collect(Collectors.toList()));
        if(goodsIds.size() != goodsResponses.size()) throw new NotFoundException("존재하지 않는 상품입니다");


        return OrderResponse.fromEntity(orderRepository.save(null));
    }

    public OrderResponse cancel(OrderRequest orderRequest) {
        OrderEntity orderEntity = orderRepository.findById(orderRequest.getOrderId()).orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));

        return OrderResponse.fromEntity(orderEntity);
    }
}