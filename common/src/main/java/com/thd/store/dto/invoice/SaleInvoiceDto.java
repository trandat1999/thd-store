package com.thd.store.dto.invoice;

import com.thd.store.dto.BaseDto;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import com.thd.store.entity.SaleInvoice;
import com.thd.store.type.PaymentType;
import com.thd.store.type.SalesInvoiceStatus;
import com.thd.store.type.SalesInvoiceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class SaleInvoiceDto extends BaseDto {
    private SalesInvoiceType type;
    private AdministrativeUnitDto province;
    private AdministrativeUnitDto district;
    private AdministrativeUnitDto commune;
    private String address;
    private String phoneNumber;
    private Double total;
    private Boolean paid;
    private PaymentType paymentType;
    private String code;
    private SalesInvoiceStatus status;
    private Date saleDate;
    private String note;
    private List<SaleItemDto> items = new ArrayList<>();

    public SaleInvoiceDto(SaleInvoice entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.code = entity.getCode();
            this.total = entity.getTotal();
            this.paid = entity.getPaid();
            this.paymentType = entity.getPaymentType();
            this.province = new AdministrativeUnitDto(entity.getProvince());
            this.district = new AdministrativeUnitDto(entity.getDistrict());
            this.commune = new AdministrativeUnitDto(entity.getCommune());
            this.address = entity.getAddress();
            this.status = entity.getStatus();
            this.phoneNumber = entity.getPhoneNumber();
            this.saleDate = entity.getSaleDate();
            this.note = entity.getNote();
        }
    }
    public SaleInvoiceDto(SaleInvoice entity, boolean items) {
        this(entity);
        if(entity!=null){
            if(!CollectionUtils.isEmpty(entity.getItems()) && items){
                for(var item : entity.getItems()){
                    this.items.add(new SaleItemDto(item));
                }
            }
        }
    }
}
