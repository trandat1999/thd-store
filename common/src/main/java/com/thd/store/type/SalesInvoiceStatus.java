package com.thd.store.type;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public enum SalesInvoiceStatus {
    NEW("New"),
    PREPARING("Preparing"),
    APPROVED("Approved"),
    TRANSPORT("Transport"),
    DELIVERED("Delivered");
    private String description;

    SalesInvoiceStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
