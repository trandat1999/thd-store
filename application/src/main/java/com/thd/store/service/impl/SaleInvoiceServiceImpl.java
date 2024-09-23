package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.invoice.SaleInvoiceDto;
import com.thd.store.dto.invoice.SaleInvoiceSearch;
import com.thd.store.entity.Product;
import com.thd.store.entity.SaleInvoice;
import com.thd.store.entity.SaleItem;
import com.thd.store.repository.AdministrativeUnitRepository;
import com.thd.store.repository.ProductRepository;
import com.thd.store.repository.SaleInvoiceRepository;
import com.thd.store.service.SaleInvoiceService;
import com.thd.store.type.SalesInvoiceStatus;
import com.thd.store.util.DateUtils;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.thd.store.util.ConstUtil.KAFKA_TOPIC_UPDATE_WAREHOUSE;

/**
 * @author DatNuclear 02/05/2024
 * @project store
 */
@Service
@AllArgsConstructor
public class SaleInvoiceServiceImpl extends BaseService implements SaleInvoiceService {
    private final SaleInvoiceRepository saleInvoiceRepository;
    private final ProductRepository productRepository;
    private final AdministrativeUnitRepository administrativeUnitRepository;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    public BaseResponse saveOrUpdate(SaleInvoiceDto request, Long id) {
        var validator = validation(request);
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        SaleInvoice entity = null;
        if (id != null) {
            Optional<SaleInvoice> optional = saleInvoiceRepository.findById(id);
            if (optional.isEmpty()) {
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.SALE_INVOICE)));
            }
            entity = optional.get();
        }
        if (entity == null) {
            entity = new SaleInvoice();
            entity.setCode(UUID.randomUUID().toString());
            entity.setStatus(SalesInvoiceStatus.NEW);
        }
        entity.setSaleDate(request.getSaleDate());
        entity.setNote(request.getNote());
        entity.setPaid(request.getPaid());
        entity.setPaymentType(entity.getPaymentType());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setType(request.getType());
        if(request.getProvince()!=null && request.getProvince().getId()!=null){
            entity.setProvince(administrativeUnitRepository.findById(request.getProvince().getId()).orElse(null));
        }
        if(request.getDistrict()!=null && request.getDistrict().getId()!=null){
            entity.setDistrict(administrativeUnitRepository.findById(request.getDistrict().getId()).orElse(null));
        }
        if(request.getCommune()!=null && request.getCommune().getId()!=null){
            entity.setCommune(administrativeUnitRepository.findById(request.getCommune().getId()).orElse(null));
        }
        entity.setAddress(request.getAddress());
        double total = 0d;
        entity.getItems().clear();
        Product product;
        for (var item : request.getItems()) {
            product = productRepository.findById(item.getProduct().getId()).orElse(null);
            if (product != null) {
                total += item.getPrice() * item.getQuantity();
                entity.getItems().add(new SaleItem(product, item.getQuantity(), item.getPrice(), entity));
            }
        }
        entity.setTotal(total);
        entity = saleInvoiceRepository.save(entity);
        return getResponse200(new SaleInvoiceDto(entity, true), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse search(SaleInvoiceSearch search) {
        if (search.getVoided() != null && !search.getVoided()) {
            search.setVoided(null);
        }
        return getResponse200(saleInvoiceRepository.search(search.getKeyword(), search.getVoided(),
                        search.getStatus(), DateUtils.atStartOfDay(search.getFromDate()),
                        DateUtils.atEndOfDay(search.getToDate()), getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getByCode(String code) {
        Optional<SaleInvoice> optional = saleInvoiceRepository.findByCode(code);
        if (optional.isPresent()) {
            return getResponse200(new SaleInvoiceDto(optional.get(), true), getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.SALE_INVOICE)));
    }

    @Override
    public BaseResponse saveDirectSale(SaleInvoiceDto request) {
        var validator = validation(request);
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        SaleInvoice entity = new SaleInvoice();
        entity.setCode(UUID.randomUUID().toString());
        entity.setStatus(SalesInvoiceStatus.DELIVERED);
        entity.setSaleDate(new Date());
        entity.setNote(request.getNote());
        entity.setPaid(true);
        entity.setPaymentType(entity.getPaymentType());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setType(request.getType());
        double total = 0d;
        entity.getItems().clear();
        Product product;
        for (var item : request.getItems()) {
            product = productRepository.findById(item.getProduct().getId()).orElse(null);
            if (product != null) {
                total += item.getPrice() * item.getQuantity();
                entity.getItems().add(new SaleItem(product, item.getQuantity(), item.getPrice(), entity));
            }
        }
        entity.setTotal(total);
        entity = saleInvoiceRepository.save(entity);
        kafkaTemplate.send(KAFKA_TOPIC_UPDATE_WAREHOUSE, entity);
        return getResponse200(new SaleInvoiceDto(entity, true), getMessage(SystemMessage.SUCCESS));
    }
}
