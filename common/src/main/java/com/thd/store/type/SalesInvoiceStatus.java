package com.thd.store.type;

import lombok.Getter;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
@Getter
public enum SalesInvoiceStatus {
    NEW("New"),
    PREPARING("Preparing"),
    APPROVED("Approved"),
    TRANSPORT("Transport"),
    DELIVERED("Delivered");
    private final String description;

    SalesInvoiceStatus(String description) {
        this.description = description;
    }

}
