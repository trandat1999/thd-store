package com.thd.store.type;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
public enum MovieAttribute {
    TIME("time","store.attribute.time")
    ;
    private String code;
    private String value;

    MovieAttribute(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
