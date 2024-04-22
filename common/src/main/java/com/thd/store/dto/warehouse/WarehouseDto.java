package com.thd.store.dto.warehouse;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import com.thd.store.entity.Warehouse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 18/04/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class WarehouseDto extends BaseInformationDto {
    @NotNull(message = "{store.validation.NotNull}")
    private AdministrativeUnitDto province;
    @NotNull(message = "{store.validation.NotNull}")
    private AdministrativeUnitDto district;
    @NotNull(message = "{store.validation.NotNull}")
    private AdministrativeUnitDto commune;
    private String address;

    public WarehouseDto(Warehouse entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.address = entity.getAddress();
            this.voided = entity.getVoided();
            if(entity.getProvince()!=null){
                this.province = new AdministrativeUnitDto(entity.getProvince());
            }
            if(entity.getDistrict()!=null){
                this.district = new AdministrativeUnitDto(entity.getDistrict());
            }
            if(entity.getCommune()!=null){
                this.commune = new AdministrativeUnitDto(entity.getCommune());
            }
        }
    }
}
