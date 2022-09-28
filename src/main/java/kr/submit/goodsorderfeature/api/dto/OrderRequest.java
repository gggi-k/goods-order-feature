package kr.submit.goodsorderfeature.api.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.submit.goodsorderfeature.api.domain.vo.Address;
import kr.submit.goodsorderfeature.api.dto.view.OrderView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.core.annotation.Order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Setter
@Getter
@ToString
public class OrderRequest {

    @Schema(hidden = true)
    private Long orderId;

    @Schema(description = "상품목록")
    @JsonView({OrderView.Create.class, OrderView.Cancel.class})
    @NotEmpty(groups = {OrderView.Create.class, OrderView.Cancel.class})
    private List<OrderGoodsRequest> orderGoodsIds;

    @Schema(description = "주소", example = "서울 강남구")
    @JsonView(OrderView.Create.class)
    @NotBlank(groups = OrderView.Create.class)
    private String address;

    @Schema(description = "상세주소", example = "테헤란로 35길")
    @JsonView(OrderView.Create.class)
    @NotBlank(groups = OrderView.Create.class)
    private String addressDetail;

    @Schema(description = "우편번호", example = "03453")
    @JsonView(OrderView.Create.class)
    @NotBlank(groups = OrderView.Create.class)
    private String zipCode;

    public Address getAddress() {
        return Address.of(this.address, this.addressDetail, this.zipCode);
    }
}