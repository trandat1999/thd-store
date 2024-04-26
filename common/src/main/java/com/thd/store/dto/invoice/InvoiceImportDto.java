package com.thd.store.dto.invoice;

import com.thd.store.dto.BaseDto;
import com.thd.store.dto.supplier.SupplierDto;
import com.thd.store.dto.warehouse.WarehouseDto;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.type.InvoiceImportType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class InvoiceImportDto extends BaseDto {
    private InvoiceImportType status;
    @NotNull(message = "{store.validation.NotNull}")
    private SupplierDto supplier;
    @NotNull(message = "{store.validation.NotNull}")
    private WarehouseDto warehouse;
    @NotNull(message = "{store.validation.NotNull}")
    private Date importDate;
    @NotEmpty(message = "{store.validation.NotEmpty}")
    @Valid
    private List<InvoiceImportItemDto> items = new ArrayList<>();
    private String code;
    private String note;

    public InvoiceImportDto(InvoiceImport entity) {
        if(entity!=null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.importDate = entity.getImportDate();
            this.status = entity.getStatus();
            this.note = entity.getNote();
            if(entity.getWarehouse()!=null) {
                this.warehouse = new WarehouseDto(entity.getWarehouse());
            }
            if(entity.getSupplier()!=null) {
                this.supplier = new SupplierDto(entity.getSupplier());
            }
        }
    }
    public InvoiceImportDto(InvoiceImport entity,boolean isItem) {
        this(entity);
        if(entity!=null) {
            if(isItem && !CollectionUtils.isEmpty(entity.getItems())){
                for(var item : entity.getItems()) {
                    items.add(new InvoiceImportItemDto(item));
                }
            }
        }
    }
}
