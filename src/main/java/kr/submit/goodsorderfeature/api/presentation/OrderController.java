package kr.submit.goodsorderfeature.api.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.goodsorderfeature.api.application.OrderService;
import kr.submit.goodsorderfeature.api.domain.entity.OrderEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.submit.goodsorderfeature.api.dto.OrderRequest;
import kr.submit.goodsorderfeature.api.dto.OrderResponse;
import kr.submit.goodsorderfeature.core.swagger.annotation.SwaggerApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@SwaggerApiResponses
@Tag(name = "주문")
@ExposesResourceFor(OrderResponse.class)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final EntityLinks entityLinks;

    @GetMapping("/{orderId}")
    public EntityModel<OrderResponse> findByOrderId(@PathVariable Long orderId) {
        return EntityModel.of(orderService.findByOrderId(orderId),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).cancelByOrderId(orderId, null)).withRel("cancel").withType(HttpMethod.PATCH.name())
                );
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Validated OrderRequest orderRequest) {

        final OrderResponse orderResponse = orderService.create(orderRequest);

        final URI location = entityLinks.linkForItemResource(OrderResponse.class, orderResponse.getOrderId()).toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @PatchMapping("/cancel/{orderId}")
    public OrderResponse cancelByOrderId(@PathVariable Long orderId, @RequestBody List<OrderGoodsRequest> orderGoodsRequests) {
        return orderService.cancel(OrderRequest.create()
                .setOrderId(orderId)
                .setOrderGoodsIds(orderGoodsRequests));
    }
}