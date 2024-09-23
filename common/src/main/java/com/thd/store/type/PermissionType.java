package com.thd.store.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DatNuclear 23/09/2024 9:14 CH
 * @project thd-store
 * @package com.thd.store.type
 */
@Getter
@AllArgsConstructor
public enum PermissionType {
    READ("Read"), WRITE("Write"), DELETE("Delete"),UPDATE("Update");
    private final String description;
}
