package com.thd.store.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DatNuclear 4:41 PM 9/23/2024
 * @project thd-store
 * @package com.thd.store.type
 */
@Getter
@AllArgsConstructor
public enum ApplicationModule {
    USER_MANAGEMENT("USER_MANAGEMENT","User Management")
    ,ADMINISTRATIVE_UNIT_MANAGEMENT("ADMINISTRATIVE_UNIT_MANAGEMENT","Administrative Unit Management")
    ;
    private final String value;
    private final String description;
}
