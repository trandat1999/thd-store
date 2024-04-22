package com.thd.store.dto.invoice;

import com.thd.store.dto.BaseDto;
import com.thd.store.dto.BaseInformationDto;
import com.thd.store.dto.supplier.SupplierDto;
import com.thd.store.dto.warehouse.WarehouseDto;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.type.InvoiceImportType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class InvoiceImportDto extends BaseInformationDto {
    @NotNull(message = "{store.validation.NotNull}")
    private InvoiceImportType status;
    @NotNull(message = "{store.validation.NotNull}")
    private SupplierDto supplier;
    @NotNull(message = "{store.validation.NotNull}")
    private WarehouseDto warehouse;
    @NotNull(message = "{store.validation.NotNull}")
    private Date importDate;
    private UUID uuidCode;

    public InvoiceImportDto(InvoiceImport entity) {
        if(entity!=null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.name = entity.getName();
            this.uuidCode = entity.getUuidCode();
            this.importDate = entity.getImportDate();
            this.status = entity.getStatus();
            if(entity.getWarehouse()!=null) {
                this.warehouse = new WarehouseDto(entity.getWarehouse());
            }
            if(entity.getSupplier()!=null) {
                this.supplier = new SupplierDto(entity.getSupplier());
            }
        }
    }
}
