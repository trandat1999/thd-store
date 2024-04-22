package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.warehouse.WarehouseDto;
import com.thd.store.dto.warehouse.WarehouseSearch;

/**
 * @author DatNuclear 18/04/2024
 * @project store
 */
public interface WarehouseService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(WarehouseDto dto, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(WarehouseSearch search);
}
