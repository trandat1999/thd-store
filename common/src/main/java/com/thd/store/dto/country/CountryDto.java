package com.thd.store.dto.country;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.entity.Country;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class CountryDto extends BaseInformationDto {
    public CountryDto(Country entity) {
        if (entity != null){
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.voided = entity.getVoided();
            this.description = entity.getDescription();
        }
    }
}
