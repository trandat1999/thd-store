package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.InvoiceImportDto;
import com.thd.store.dto.invoice.InvoiceImportSearch;
import com.thd.store.type.InvoiceImportType;

import java.util.UUID;

/**
 * @author DatNuclear 24/04/2024
 * @project store
 */
public interface InvoiceImportService {
    BaseResponse getById(Long id);
    BaseResponse getByCode(String code);
    BaseResponse saveOrUpdate(InvoiceImportDto request, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(InvoiceImportSearch search);
    BaseResponse changeStatus(InvoiceImportType status,String code);
}
