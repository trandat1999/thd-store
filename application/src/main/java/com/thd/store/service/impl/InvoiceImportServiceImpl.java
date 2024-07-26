package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.InvoiceImportDto;
import com.thd.store.dto.invoice.InvoiceImportSearch;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.entity.InvoiceImportItem;
import com.thd.store.entity.Product;
import com.thd.store.repository.*;
import com.thd.store.service.InventoryService;
import com.thd.store.service.InvoiceImportService;
import com.thd.store.type.InvoiceImportType;
import com.thd.store.util.DateUtils;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DatNuclear 24/04/2024
 * @project store
 */
@Service
@AllArgsConstructor
public class InvoiceImportServiceImpl extends BaseService implements InvoiceImportService {
    private final InvoiceImportRepository invoiceImportRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final InvoiceImportItemRepository invoiceImportItemRepository;

    @Override
    public BaseResponse getById(Long id) {
        Optional<InvoiceImport> optional = invoiceImportRepository.findById(id);
        if (optional.isPresent()){
            return getResponse200(new InvoiceImportDto(optional.get(),true),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.INVOICE_IMPORT)));
    }

    @Override
    public BaseResponse getByCode(String code) {
        Optional<InvoiceImport> optional = invoiceImportRepository.findByCode(code);
        if (optional.isPresent()){
            return getResponse200(new InvoiceImportDto(optional.get(),true),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.INVOICE_IMPORT)));
    }

    @Override
    public BaseResponse saveOrUpdate(InvoiceImportDto request, Long id) {
        var validator = validation(request);
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        InvoiceImport entity = null;
        if (id != null) {
            Optional<InvoiceImport> optional = invoiceImportRepository.findById(id);
            if (optional.isEmpty()) {
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.INVOICE_IMPORT)));
            }
            entity = optional.get();
        }
        if (entity == null) {
            entity = new InvoiceImport();
            entity.setCode(UUID.randomUUID().toString());
            entity.setStatus(InvoiceImportType.NEW);
        }
        entity.setImportDate(request.getImportDate());
        entity.setNote(request.getNote());
        entity.setSupplier(supplierRepository.findById(request.getSupplier().getId()).orElse(null));
        entity.setWarehouse(warehouseRepository.findById(request.getWarehouse().getId()).orElse(null));
        Double total = 0d;
        entity.getItems().clear();
        Product product;
        for(var item : request.getItems()){
            product = productRepository.findById(item.getProduct().getId()).orElse(null);
            if (product!=null){
                total+= item.getPrice()* item.getQuantity();
                entity.getItems().add(new InvoiceImportItem(product, item.getQuantity(), item.getPrice(), entity));
            }
        }
        entity.setTotal(total);
        entity = invoiceImportRepository.save(entity);
        return getResponse200(new InvoiceImportDto(entity,true),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        return null;
    }

    @Override
    public BaseResponse search(InvoiceImportSearch search) {
        if (search.getVoided() != null && !search.getVoided()) {
            search.setVoided(null);
        }
        return getResponse200(invoiceImportRepository.search(search.getKeyword(), search.getVoided(),
                        search.getStatus(), DateUtils.atStartOfDay(search.getFromDate()),
                        DateUtils.atEndOfDay(search.getToDate()),getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse changeStatus(InvoiceImportType status,String code) {
        Optional<InvoiceImport> optional = invoiceImportRepository.findByCode(code);
        if (!optional.isPresent()){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.INVOICE_IMPORT)));
        }
        InvoiceImport entity = optional.get();
        entity.setStatus(status);
        entity = invoiceImportRepository.save(entity);
        if(status.equals(InvoiceImportType.WAREHOUSED)){
            inventoryService.warehoused(entity);
        }
        return getResponse200(new InvoiceImportDto(entity,true),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getLastPriceImported(Long productId) {
        return getResponse200(invoiceImportItemRepository.findFirstPriceByProductIdOrderByCreatedDateDesc(productId),
                getMessage(SystemMessage.SUCCESS));
    }
}
