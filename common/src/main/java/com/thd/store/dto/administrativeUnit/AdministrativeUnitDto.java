package com.thd.store.dto.administrativeUnit;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.entity.AdministrativeUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class AdministrativeUnitDto extends BaseInformationDto {
    private AdministrativeUnitDto parent;
    private String shortName;
    private Integer level;

    public AdministrativeUnitDto(AdministrativeUnit entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.shortName = entity.getShortName();
            this.level = entity.getLevel();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.voided = entity.getVoided();
        }
    }
    public AdministrativeUnitDto(AdministrativeUnit entity, boolean parent) {
        this(entity);
        if(entity!=null){
            if(parent && entity.getParent()!=null){
                this.parent = new AdministrativeUnitDto(entity.getParent());
            }
        }
    }
}
