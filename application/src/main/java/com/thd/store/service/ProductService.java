package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.dto.product.ProductSearch;

/**
 * @author DatNuclear 05/04/2024
 * @project store
 */
public interface ProductService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(ProductDto request, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(ProductSearch search);
}
