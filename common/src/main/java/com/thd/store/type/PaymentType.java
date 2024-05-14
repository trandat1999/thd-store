package com.thd.store.type;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public enum PaymentType {
    CASH("Cash"),
    CARD("Card"),
    COD("Cash on Delivery"),
    TRANSFER_MONEY("Transfer money");
    private String description;

    PaymentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
