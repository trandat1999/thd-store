package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.entity.SaleInvoice;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public interface InventoryService {
    void warehoused(InvoiceImport invoiceImport);
    void outStock(SaleInvoice saleInvoice);
}
