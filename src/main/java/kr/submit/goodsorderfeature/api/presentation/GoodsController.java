package kr.submit.goodsorderfeature.api.presentation;

import kr.submit.goodsorderfeature.api.application.GoodsService;
import kr.submit.goodsorderfeature.api.dto.GoodsRequest;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping
    public ResponseEntity<GoodsResponse> create(@Validated @RequestBody GoodsRequest goodsRequest) {

        return ResponseEntity.created(null).body(null);
    }

    @PutMapping("/{goodsId}")
    public ResponseEntity<GoodsResponse> update(@PathVariable String goodsId, @Validated @RequestBody GoodsRequest goodsRequest) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{goodsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByGoodsId(@PathVariable String goodsId) {

    }
}