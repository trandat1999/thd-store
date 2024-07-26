package com.thd.store.type;

import lombok.Getter;

@Getter
public enum ProductShowType {
    IN_STOCK(1),
    OUT_OF_STOCK(2),
    NEW(3),
    DISCONTINUED(4),
    LIMITED_STOCK(5),
    ON_SALE(6),
    UNAVAILABLE(7);
    private final int value;
    ProductShowType(int value) {
        this.value = value;
    }
}
