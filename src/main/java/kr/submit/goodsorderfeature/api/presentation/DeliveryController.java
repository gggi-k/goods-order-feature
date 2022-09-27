package kr.submit.goodsorderfeature.api.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.submit.goodsorderfeature.api.application.DeliveryService;
import kr.submit.goodsorderfeature.api.dto.DeliveryResponse;
import kr.submit.goodsorderfeature.api.dto.OrderResponse;
import kr.submit.goodsorderfeature.core.swagger.annotation.SwaggerApiResponses;
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

    @PatchMapping("/complete/{deliveryId}")
    public DeliveryResponse complete(@PathVariable Long deliveryId) {
        return deliveryService.complete(deliveryId);
    }
}