package org.mwt.market.domain.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mwt.market.common.exception.ErrorCode;
import org.mwt.market.domain.product.exception.NoSuchStatusException;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    TRADE(1, "판매중"),
    RESERVATION(2, "예약중"),
    COMPLETED(3, "거래완료");

    private final Integer dbIdx;
    private final String value;

    public static ProductStatus getFromDbIdx(Integer idx) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.getDbIdx().equals(idx))
                return productStatus;
        }
        throw new NoSuchStatusException(ErrorCode.NoSuchElement);
    }
}
