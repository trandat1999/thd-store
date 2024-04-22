package com.thd.store.dto.warehouse;

import com.thd.store.dto.SearchRequest;
import lombok.Data;

/**
 * @author DatNuclear 18/04/2024
 * @project store
 */
@Data
public class WarehouseSearch extends SearchRequest {
    private Long provinceId;
    private Long districtId;
    private Long communeId;
}
