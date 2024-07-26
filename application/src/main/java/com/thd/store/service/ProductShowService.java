package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductSearch;
import com.thd.store.dto.product.ProductShowDto;

public interface ProductShowService {
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(ProductShowDto request, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(ProductSearch search);
}
