package com.thd.store.type;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
public enum AdministrativeUnitLevel {
    PROVINCE(1),DISTRICT(2),COMMUNE(3);
    private int level;

    AdministrativeUnitLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
