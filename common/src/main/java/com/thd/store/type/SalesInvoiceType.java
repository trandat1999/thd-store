package com.thd.store.type;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public enum SalesInvoiceType {
    STANDARD("Standard"),ONLINE("Online");
    private String description;

    SalesInvoiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
