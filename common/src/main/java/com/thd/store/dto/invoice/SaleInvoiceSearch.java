package com.thd.store.dto.invoice;

import com.thd.store.dto.SearchRequest;
import com.thd.store.type.SalesInvoiceStatus;
import lombok.Data;

import java.util.Date;

/**
 * @author DatNuclear 02/05/2024
 * @project store
 */
@Data
public class SaleInvoiceSearch extends SearchRequest {
    private Date fromDate;
    private Date toDate;
    private SalesInvoiceStatus status;
}
