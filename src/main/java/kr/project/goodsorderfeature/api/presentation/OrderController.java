package kr.project.goodsorderfeature.api.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.project.goodsorderfeature.api.dto.OrderGoodsRequest;
import kr.project.goodsorderfeature.api.dto.OrderRequest;
import kr.project.goodsorderfeature.api.dto.view.OrderView;
import kr.project.goodsorderfeature.core.swagger.annotation.SwaggerApiResponses;
import kr.project.goodsorderfeature.api.application.OrderService;
import kr.project.goodsorderfeature.api.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "주문 조회")
    @GetMapping("/{orderId}")
    public EntityModel<OrderResponse> findByOrderId(@PathVariable Long orderId) {
        return EntityModel.of(orderService.findByOrderId(orderId),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).cancelByOrderId(orderId, null)).withRel("cancel").withType(HttpMethod.PATCH.name())
                );
    }

    @Operation(summary = "주문 등록")
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Validated(OrderView.Create.class)
                                                @JsonView(OrderView.Create.class)
                                                @RequestBody OrderRequest orderRequest) {

        final OrderResponse orderResponse = orderService.create(orderRequest);

        final URI location = entityLinks.linkForItemResource(OrderResponse.class, orderResponse.getOrderId()).toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @Operation(summary = "주문 부분 취소")
    @PatchMapping("/cancel/{orderId}")
    public OrderResponse cancelByOrderId(@PathVariable Long orderId,
                                         @Validated(OrderView.Cancel.class)
                                         @JsonView(OrderView.Cancel.class)
                                         @RequestBody List<OrderGoodsRequest> orderGoodsRequests) {

        return orderService.cancel(OrderRequest.create()
                .setOrderId(orderId)
                .setOrderGoodsIds(orderGoodsRequests));
    }
}