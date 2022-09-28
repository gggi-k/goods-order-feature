package kr.submit.goodsorderfeature.api.application;

import kr.submit.goodsorderfeature.annotation.GoodsTag;
import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsRequest;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import kr.submit.goodsorderfeature.api.repository.GoodsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@GoodsTag
@ExtendWith(MockitoExtension.class)
public class GoodsServiceTest {

    @InjectMocks
    private GoodsService goodsService;

    @Mock
    private GoodsRepository goodsRepository;

    private GoodsEntity goodsEntity;
    private GoodsRequest goodsRequest;

    @BeforeEach
    void init() {
        this.goodsEntity = GoodsEntity.builder()
                .goodsId(1L)
                .name("상품명입니당")
                .price(35000)
                .build();

        this.goodsRequest = GoodsRequest.create()
                .setGoodsId(1L)
                .setName("상품명입니당")
                .setPrice(35000);
    }

    @DisplayName("상품 목록 페이징 조회")
    @Test
    void findAllByPageable() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        BDDMockito.given(goodsRepository.findAll(BDDMockito.any(Pageable.class)))
                .willReturn(new PageImpl<GoodsEntity>(List.of(goodsEntity)));

        Page<GoodsResponse> goodsResponsePage = goodsService.findAllByPageable(pageRequest);

        Assertions.assertEquals(goodsResponsePage.getTotalElements(), 1);
        Assertions.assertEquals(goodsResponsePage.getContent().get(0).getGoodsId(), 1L);
        Assertions.assertEquals(goodsResponsePage.getContent().get(0).getName(), "상품명입니당");
        Assertions.assertEquals(goodsResponsePage.getContent().get(0).getPrice(), 35000);
    }

    @DisplayName("상품 단일 조회")
    @Test
    void findByGoodsId() {

        Long goodsId = goodsEntity.getGoodsId();

        BDDMockito.given(goodsRepository.findById(BDDMockito.anyLong()))
                .willReturn(Optional.of(goodsEntity));

        GoodsResponse goodsResponse = goodsService.findByGoodsId(goodsId);

        Assertions.assertEquals(goodsResponse.getGoodsId(), 1L);
        Assertions.assertEquals(goodsResponse.getName(), "상품명입니당");
        Assertions.assertEquals(goodsResponse.getPrice(), 35000);
    }

    @DisplayName("상품 목록 포함 조회")
    @Test
    void findAllByGoodsIds() {

        Long goodsId = goodsEntity.getGoodsId();

        BDDMockito.given(goodsRepository.findAllById(BDDMockito.anyIterable()))
                .willReturn(List.of(goodsEntity));

        List<GoodsResponse> goodsResponses = goodsService.findAllByGoodsIds(List.of(goodsId));

        Assertions.assertEquals(goodsResponses.size(), 1);
        Assertions.assertEquals(goodsResponses.get(0).getGoodsId(), 1);
        Assertions.assertEquals(goodsResponses.get(0).getName(), "상품명입니당");
        Assertions.assertEquals(goodsResponses.get(0).getPrice(), 35000);
    }

    @DisplayName("상품 등록")
    @Test
    void create() {

    }

    @DisplayName("상품 수정")
    @Test
    void update() {

    }

    @DisplayName("상품 삭제")
    @Test
    void deleteByGoodsId() {

        Long goodsId = goodsEntity.getGoodsId();


        goodsService.deleteByGoodsId(goodsId);

    }
}