package com.thd.store.dto.invoice;

import com.thd.store.dto.SearchRequest;
import com.thd.store.type.InvoiceImportType;
import lombok.Data;

import java.util.Date;

/**
 * @author DatNuclear 24/04/2024
 * @project store
 */
@Data
public class InvoiceImportSearch extends SearchRequest {
    private Long warehouseId;
    private Date fromDate;
    private Date toDate;
    private InvoiceImportType status;
}
