package com.thd.store.dto.product;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.dto.file.FileDto;
import com.thd.store.entity.Product;
import com.thd.store.entity.ProductFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class ProductDto extends BaseInformationDto {
    private List<FileDto> files = new ArrayList<>();
    private Map<String, String> attributes = new HashMap<>();

    public ProductDto(Product entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.shortDescription = entity.getShortDescription();
            this.voided = entity.getVoided();
            if(!CollectionUtils.isEmpty(entity.getFiles())){
                for(ProductFile file : entity.getFiles()) {
                    this.files.add(new FileDto(file.getFile()));
                }
            }
            this.attributes = entity.getAttributes();
        }
    }
}
