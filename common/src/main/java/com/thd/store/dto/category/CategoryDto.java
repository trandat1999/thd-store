package com.thd.store.dto.category;

import com.thd.store.dto.BaseDto;
import com.thd.store.dto.BaseInformationDto;
import com.thd.store.entity.BaseInformation;
import com.thd.store.entity.Category;
import com.thd.store.util.anotation.RequiredConditionGOET;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author DatNuclear 03/02/2024
 * @project store-movie
 */
@Data
@RequiredConditionGOET(fieldName = "parentId", dependField = "level", value = 2)
public class CategoryDto extends BaseInformationDto {
    @NotNull(message = "{store.validation.NotNull}")
    @Min(value = 1,message = "{store.validation.Min}")
    @Max(value = 3,message = "{store.validation.Max}")
    private Integer level;
    private Long parentId;

    public CategoryDto() {
    }
    public CategoryDto(Category entity) {
        if(entity != null){
            this.id = entity.getId();
            this.voided = entity.getVoided();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.level = entity.getLevel();
            this.parentId = entity.getParentId();
        }
    }
}
