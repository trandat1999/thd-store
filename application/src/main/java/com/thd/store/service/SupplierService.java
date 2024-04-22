package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.supplier.SupplierDto;

/**
 * @author DatNuclear 21/03/2024
 * @project store
 */
public interface SupplierService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(SupplierDto category, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(SearchRequest search);
}
