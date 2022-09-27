package kr.submit.goodsorderfeature.api.dto;

import kr.submit.goodsorderfeature.api.domain.vo.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@Setter
@Getter
@ToString
public class OrderRequest {

    private Long orderId;
    private List<OrderGoodsRequest> orderGoodsIds;
    private String address;
    private String addressDetail;
    private String zipCode;

    public Address getAddress() {
        return Address.of(this.address, this.addressDetail, this.zipCode);
    }
}