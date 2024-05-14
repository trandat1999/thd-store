package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.InvoiceImportDto;
import com.thd.store.dto.invoice.InvoiceImportSearch;
import com.thd.store.dto.invoice.SaleInvoiceDto;
import com.thd.store.dto.invoice.SaleInvoiceSearch;
import com.thd.store.service.SaleInvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 03/05/2024
 * @project store
 */
@RestController
@RequestMapping(value = "/api/v1/sale-invoices")
@AllArgsConstructor
public class SaleInvoiceController {
    private final SaleInvoiceService saleInvoiceService;
    @PostMapping
    public BaseResponse save(@RequestBody SaleInvoiceDto request){
        return saleInvoiceService.saveOrUpdate(request,request.getId());
    }
    @PostMapping("/pages")
    public BaseResponse search(@RequestBody SaleInvoiceSearch request){
        return saleInvoiceService.search(request);
    }
    @GetMapping("/code/{code}")
    public BaseResponse getByCode(@PathVariable String code){
        return saleInvoiceService.getByCode(code);
    }
}
