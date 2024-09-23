package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.SaleInvoiceDto;
import com.thd.store.dto.invoice.SaleInvoiceSearch;

/**
 * @author DatNuclear 02/05/2024
 * @project store
 */
public interface SaleInvoiceService {
    BaseResponse saveOrUpdate(SaleInvoiceDto request,Long id);
    BaseResponse search(SaleInvoiceSearch request);
    BaseResponse getByCode(String code);
    BaseResponse saveDirectSale(SaleInvoiceDto request);
}
