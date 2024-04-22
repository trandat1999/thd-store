package com.thd.store.type;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
public enum InvoiceImportType {
    ORDER("Order"),
    NEW("New"),
    PENDING_APPROVAL("Pending Approval"),
    APPROVED("Approved"),
    PAID("Paid"),
    TRANSPORT("Transport"),
    WAREHOUSED("Warehoused");
    private String description;

    InvoiceImportType(String description) {
    }

    public String getDescription() {
        return description;
    }
}
