package com.thd.store.type;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public enum InventoryStatusType {
    IN_BOUND(1),OUT_BOUND(-1);
    private int status;

    InventoryStatusType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
