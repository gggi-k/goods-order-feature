package kr.submit.goodsorderfeature.api.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.submit.goodsorderfeature.annotation.GoodsTag;
import kr.submit.goodsorderfeature.api.application.GoodsService;
import kr.submit.goodsorderfeature.api.domain.entity.GoodsEntity;
import kr.submit.goodsorderfeature.api.dto.GoodsRequest;
import kr.submit.goodsorderfeature.api.dto.GoodsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WithMockUser
@WebMvcTest(GoodsController.class)
@GoodsTag
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityLinks entityLinks;

    @MockBean
    private GoodsService goodsService;

    private GoodsRequest goodsRequest;

    private GoodsEntity goodsEntity;

    @BeforeEach
    void init() {
        goodsRequest = GoodsRequest.create()
                .setName("상품명입니당")
                .setPrice(14000);

        goodsEntity = GoodsEntity.builder()
                .goodsId(1L)
                .name("상품명입니당")
                .price(14000)
                .build();
    }


    @DisplayName("상품 목록 페이징 조회")
    @Test
    void findAllByPageable() throws Exception {


        Page<GoodsResponse> goodsResponsePage = GoodsResponse.fromEntitiesPage(new PageImpl<GoodsEntity>(List.of(this.goodsEntity)));

        BDDMockito.given(goodsService.findAllByPageable(BDDMockito.any()))
                .willReturn(goodsResponsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods")
                .contentType(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.content[0].goodsId").value(1L),
                        MockMvcResultMatchers.jsonPath("$.content[0].name").value("상품명입니당"),
                        MockMvcResultMatchers.jsonPath("$.content[0].price").value(14000)
                )
                .andDo(print());
    }


    @DisplayName("상품 단일 조회")
    @Test
    void findByGoodsId() throws Exception {

        Long goodsId = goodsEntity.getGoodsId();
        GoodsResponse goodsResponse = GoodsResponse.fromEntity(this.goodsEntity);

        BDDMockito.given(goodsService.findByGoodsId(BDDMockito.anyLong()))
                .willReturn(goodsResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/{goodsId}", goodsId)
                .contentType(MediaTypes.HAL_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.goodsId").value(1L),
                        MockMvcResultMatchers.jsonPath("$.name").value("상품명입니당"),
                        MockMvcResultMatchers.jsonPath("$.price").value(14000),
                        MockMvcResultMatchers.jsonPath("$._links.update.href").exists(),
                        MockMvcResultMatchers.jsonPath("$._links.update.type").exists(),
                        MockMvcResultMatchers.jsonPath("$._links.delete.href").exists(),
                        MockMvcResultMatchers.jsonPath("$._links.delete.type").exists()
                )
                .andDo(print());

    }

    @DisplayName("상품 등록")
    @Test
    void create() throws Exception {

        GoodsResponse goodsResponse = GoodsResponse.fromEntity(this.goodsEntity);

        BDDMockito.given(goodsService.create(BDDMockito.any()))
                .willReturn(goodsResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods")
                .contentType(MediaTypes.HAL_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(objectMapper.writeValueAsString(goodsRequest)))
                .andExpectAll(
                    MockMvcResultMatchers.status().isCreated(),
                    MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION)
                )
                .andExpectAll(
                    MockMvcResultMatchers.jsonPath("$.goodsId").value(1L),
                    MockMvcResultMatchers.jsonPath("$.name").value("상품명입니당"),
                    MockMvcResultMatchers.jsonPath("$.price").value(14000)
                )
                .andDo(print());

    }

    @DisplayName("상품 수정")
    @Test
    void update() throws Exception {

        Long goodsId = goodsEntity.getGoodsId();

        BDDMockito.given(goodsService.update(BDDMockito.any()))
                .willReturn(GoodsResponse.fromEntity(goodsEntity));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/goods/{goodsId}", goodsId)
                .contentType(MediaTypes.HAL_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(objectMapper.writeValueAsString(goodsRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$.goodsId").value(1L),
                        MockMvcResultMatchers.jsonPath("$.name").value("상품명입니당"),
                        MockMvcResultMatchers.jsonPath("$.price").value(14000)
                )
                .andDo(print());
    }

    @DisplayName("상품 단일 삭제")
    @Test
    void deleteByGoodsId() throws Exception {

        Long goodsId = goodsEntity.getGoodsId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/goods/{goodsId}", goodsId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }
}