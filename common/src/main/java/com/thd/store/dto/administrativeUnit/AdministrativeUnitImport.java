package com.thd.store.dto.administrativeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeUnitImport {
    private String name;
    private String code;
    private String parentCode;
    private int level;

    public boolean isValid() {
        return StringUtils.hasText(name) && StringUtils.hasText(code);
    }
}
