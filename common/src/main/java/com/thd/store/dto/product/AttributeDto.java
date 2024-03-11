package com.thd.store.dto.product;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.entity.Attribute;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class AttributeDto extends BaseInformationDto {
    public AttributeDto(Attribute entity) {
        if(entity!=null){
            this.id=entity.getId();
            this.name=entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.shortDescription = entity.getShortDescription();
            this.voided = entity.getVoided();
        }
    }
}
