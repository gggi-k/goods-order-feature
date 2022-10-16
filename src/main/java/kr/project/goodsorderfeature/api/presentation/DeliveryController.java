package kr.project.goodsorderfeature.api.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.project.goodsorderfeature.api.dto.DeliveryResponse;
import kr.project.goodsorderfeature.api.application.DeliveryService;
import kr.project.goodsorderfeature.api.dto.OrderResponse;
import kr.project.goodsorderfeature.core.swagger.annotation.SwaggerApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SwaggerApiResponses
@Tag(name = "배달")
@ExposesResourceFor(OrderResponse.class)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "배달 완료")
    @PatchMapping("/complete/{deliveryId}")
    public DeliveryResponse complete(@PathVariable Long deliveryId) {
        return deliveryService.complete(deliveryId);
    }
}