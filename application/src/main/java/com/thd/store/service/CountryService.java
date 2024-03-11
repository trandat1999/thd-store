package com.thd.store.service;

import com.thd.store.dto.country.CountryDto;
import com.thd.store.dto.country.CountrySearch;
import com.thd.store.dto.BaseResponse;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
public interface CountryService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(CountryDto dto, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(CountrySearch search);
}
