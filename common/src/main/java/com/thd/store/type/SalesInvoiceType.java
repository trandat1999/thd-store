package com.thd.store.type;

import lombok.Getter;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
@Getter
public enum SalesInvoiceType {
    STANDARD("Standard"),ONLINE("Online");
    private final String description;

    SalesInvoiceType(String description) {
        this.description = description;
    }

}
