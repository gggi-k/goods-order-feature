package kr.submit.goodsorderfeature.api.application;

import kr.submit.goodsorderfeature.api.domain.GoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsRequest;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsResponse create(GoodsRequest goodsRequest) {

        return GoodsResponse.fromEntity(goodsRepository.save(GoodsEntity
                .builder()
                .goodsId(goodsRequest.getGoodsId())
                .build()));
    }

    public GoodsResponse update(GoodsRequest goodsRequest) {

//        goodsRepository.findById(goodsRequest.getGoodsId()).orElseThrow(() -> new)

        return GoodsResponse.fromEntity(goodsRepository.save(null));
    }

    public void deleteByGoodsId(String goodsId) {
        goodsRepository.deleteById(goodsId);
    }
}