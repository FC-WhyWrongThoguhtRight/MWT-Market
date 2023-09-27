package org.mwt.market.domain.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatusType {
    TRADE("판매중"),
    COMPLETED("거래완료"),
    RESERVATION("예약중");

    private final String value;

}
