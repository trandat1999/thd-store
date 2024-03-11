package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.product.AttributeDto;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
public interface AttributeService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(AttributeDto category, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(SearchRequest search);
}
