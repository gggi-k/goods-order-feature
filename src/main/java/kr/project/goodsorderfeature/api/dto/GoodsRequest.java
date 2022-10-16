package kr.project.goodsorderfeature.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Setter
@Getter
@ToString
public class GoodsRequest {

    @Schema(hidden = true)
    private Long goodsId;

    @NotBlank
    @Schema(description = "상품명", example = "상품1")
    private String name;

    @Schema(description = "가격", example = "1000")
    private long price;
}