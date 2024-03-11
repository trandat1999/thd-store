package com.thd.store.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DatNuclear 16/01/2024
 * @project store-movie
 */
@Data
@EqualsAndHashCode
public class SearchRequest {
    protected Boolean voided;
    protected String keyword;
    protected Integer pageSize;
    protected Integer pageIndex;
}
