package com.thd.store.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Data
public class BaseDto implements Serializable {
    protected Long id;
    protected Boolean voided;
}
