package kr.project.goodsorderfeature.api.domain.vo;

import kr.project.goodsorderfeature.core.error.ForbiddenException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(staticName = "of")
@Getter
public class OrderGoodsSummary {

    @EqualsAndHashCode.Include
    private final Long goodsId;
    private final String name;
    private final int count;
    private final long price;

    public OrderGoodsSummary addCount(int count) {
        if(this.isNotCountOverZero(count)) throw new ForbiddenException("0 이하의 갯수가 될수없습니다");

        return OrderGoodsSummary.of(this.goodsId, this.name, this.count + count, this.price);
    }

    public boolean isNotCountOverZero(int count) {
        return (this.count - count) <= 0;
    }
}