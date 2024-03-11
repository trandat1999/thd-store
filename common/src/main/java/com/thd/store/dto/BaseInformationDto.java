package com.thd.store.dto;

import lombok.Data;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Data
public class BaseInformationDto extends BaseDto{
    protected String code;
    protected String name;
    protected String description;
    protected String shortDescription;
}
