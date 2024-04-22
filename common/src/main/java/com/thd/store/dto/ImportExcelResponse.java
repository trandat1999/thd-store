package com.thd.store.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Data
@Builder
public class ImportExcelResponse {
    private Long total;
    private Long success;
    private Long failure;
    private Object totalList;
    private Object successList;
    private Object failureList;
}
