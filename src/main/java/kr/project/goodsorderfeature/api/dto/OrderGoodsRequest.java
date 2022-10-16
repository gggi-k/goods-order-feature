package kr.project.goodsorderfeature.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.project.goodsorderfeature.api.dto.view.OrderView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Setter
@Getter
@ToString
public class OrderGoodsRequest {

    @Schema(description = "상품아이디", example = "1")
    @JsonView({OrderView.Create.class, OrderView.Cancel.class})
    @NotEmpty(groups = {OrderView.Create.class, OrderView.Cancel.class})
    private Long goodsId;

    @Schema(description = "상품명", example = "상품입니당")
    @JsonView(OrderView.Create.class)
    @NotEmpty(groups = OrderView.Create.class)
    private String name;

    @Schema(description = "가격", example = "2000")
    @JsonView(OrderView.Create.class)
    @NotEmpty(groups = OrderView.Create.class)
    private long price;

    @Schema(description = "갯수", example = "3")
    @JsonView({OrderView.Create.class, OrderView.Cancel.class})
    @Positive(groups = {OrderView.Create.class, OrderView.Cancel.class})
    private int count;
}