package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.InvoiceImportDto;
import com.thd.store.dto.invoice.InvoiceImportSearch;
import com.thd.store.service.InvoiceImportService;
import com.thd.store.type.InvoiceImportType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 25/04/2024
 * @project store
 */
@RestController
@RequestMapping(value = "/api/v1/invoice-imports")
@AllArgsConstructor
public class InvoiceImportController {
    private final InvoiceImportService invoiceImportService;
    @PostMapping
    public BaseResponse save(@RequestBody InvoiceImportDto request){
        return invoiceImportService.saveOrUpdate(request,request.getId());
    }
    @PostMapping("/pages")
    public BaseResponse search(@RequestBody InvoiceImportSearch request){
        return invoiceImportService.search(request);
    }
    @GetMapping("/code/{code}")
    public BaseResponse getByCode(@PathVariable String code){
        return invoiceImportService.getByCode(code);
    }
    @GetMapping("/{code}/order")
    public BaseResponse changeStatusOrder(@PathVariable String code){
        return invoiceImportService.changeStatus(InvoiceImportType.ORDER,code);
    }
    @GetMapping("/{code}/approve")
    public BaseResponse changeStatusApprove(@PathVariable String code){
        return invoiceImportService.changeStatus(InvoiceImportType.APPROVED,code);
    }
    @GetMapping("/{code}/ship")
    public BaseResponse changeStatusTransport(@PathVariable String code){
        return invoiceImportService.changeStatus(InvoiceImportType.TRANSPORT,code);
    }
    @GetMapping("/{code}/paid")
    public BaseResponse changeStatusPaid(@PathVariable String code){
        return invoiceImportService.changeStatus(InvoiceImportType.PAID,code);
    }
    @GetMapping("/{code}/warehoused")
    public BaseResponse changeStatusWarehoused(@PathVariable String code){
        return invoiceImportService.changeStatus(InvoiceImportType.WAREHOUSED,code);
    }
}
