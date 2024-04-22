package com.thd.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Data
public class BaseInformationDto extends BaseDto{
    @NotNull(message = "{store.validation.NotNull}")
    @NotBlank(message = "{store.validation.NotBlank}")
    protected String code;
    @NotNull(message = "{store.validation.NotNull}")
    @NotBlank(message = "{store.validation.NotBlank}")
    protected String name;
    protected String description;
    protected String shortDescription;
}
