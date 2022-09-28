package kr.submit.goodsorderfeature.api.application;

import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsRequest;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.repository.GoodsRepository;
import kr.submit.goodsorderfeature.core.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public Page<GoodsResponse> findAllByPageable(Pageable pageable) {
        return GoodsResponse.fromEntitiesPage(goodsRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public GoodsResponse findByGoodsId(Long goodsId) {
        return GoodsResponse.fromEntity(goodsRepository.findById(goodsId).orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다")));
    }

    public GoodsResponse create(GoodsRequest goodsRequest) {

        return GoodsResponse.fromEntity(goodsRepository.save(GoodsEntity.builder()
                .name(goodsRequest.getName())
                .price(goodsRequest.getPrice())
                .build()));
    }

    public GoodsResponse update(GoodsRequest goodsRequest) {

        GoodsEntity goodsEntity = goodsRepository.findById(goodsRequest.getGoodsId()).orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다"));

        return GoodsResponse.fromEntity(goodsRepository.save(goodsEntity
                .setName(goodsRequest.getName())
                .setPrice(goodsRequest.getPrice())));
    }

    public void deleteByGoodsId(Long goodsId) {
        goodsRepository.deleteById(goodsId);
    }
}