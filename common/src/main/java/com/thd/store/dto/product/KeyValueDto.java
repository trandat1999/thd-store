package com.thd.store.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 08/04/2024
 * @project store
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KeyValueDto {
    private String key;
    private String value;
}
