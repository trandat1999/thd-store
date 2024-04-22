package com.thd.store.service;

import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.category.CategorySearch;
import com.thd.store.dto.BaseResponse;

/**
 * @author DatNuclear 05/02/2024
 * @project store-movie
 */
public interface CategoryService {
    BaseResponse getAll();
    BaseResponse getAllByLevel(Integer level);
    BaseResponse getAllByParentId(Long parentId);
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(CategoryDto category, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(CategorySearch search);
}
