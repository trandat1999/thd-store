package com.thd.store.dto.product;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.file.FileDto;
import com.thd.store.entity.Product;
import com.thd.store.entity.ProductFile;
import com.thd.store.entity.ProductShow;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ProductDto extends BaseInformationDto {
    @NotNull(message = "{store.validation.NotNull}")
    @NotEmpty(message = "{store.validation.NotEmpty}")
    private List<FileDto> files = new ArrayList<>();
    private Set<KeyValueDto> attributes = new HashSet<>();
    @NotEmpty(message = "{store.validation.NotEmpty}")
    private List<CategoryDto> categories = new ArrayList<>();

    private Double price;
    private Integer status;

    public ProductDto(Product entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.shortDescription = entity.getShortDescription();
            this.voided = entity.getVoided();
        }
    }
    public ProductDto(Product entity,boolean files,boolean attributes) {
        this(entity);
        if(entity!=null){
            if(!CollectionUtils.isEmpty(entity.getFiles()) && files){
                for(ProductFile file : entity.getFiles()) {
                    this.files.add(new FileDto(file.getFile()));
                }
            }
            if(attributes && !CollectionUtils.isEmpty(entity.getAttributes())){
                for(var attribute : entity.getAttributes().entrySet()) {
                    this.attributes.add(new KeyValueDto(attribute.getKey(), attribute.getValue()));
                }
            }
        }
    }
    public ProductDto(Product entity,boolean files,boolean attributes,boolean category) {
        this(entity,files,attributes);
        if(entity!=null){
            if(category && !CollectionUtils.isEmpty(entity.getCategories())){
                for(var item : entity.getCategories()) {
                    if(item.getCategory()!=null && (item.getCategory().getVoided()==null || !item.getCategory().getVoided())){
                        this.categories.add(new CategoryDto(item.getCategory()));
                    }
                }
            }
        }
    }
    public ProductDto(ProductShow entity) {
        this(entity==null?null:entity.getProduct());
        if(entity!=null){
            this.price = entity.getPrice();
            this.status = entity.getStatus();
        }
    }
}
