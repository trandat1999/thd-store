package com.thd.store.elasticsearch.dto;

import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.file.FileDto;
import com.thd.store.dto.product.KeyValueDto;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.entity.ProductShow;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Datnuclear 01/08/2024 14:45
 * @project thd-store
 * @package com.thd.store.dto.elasticsearch
 **/
@Document(indexName = "product")
@Data
public class ProductES {
    private Long id;
    protected String code;
    protected String name;
    protected String description;
    protected String shortDescription;
    private List<FileDto> files = new ArrayList<>();
    private Set<KeyValueDto> attributes = new HashSet<>();
    private List<CategoryDto> categories = new ArrayList<>();
    private Double price;
    private Integer status;

    public ProductES() {
    }
    public ProductES(ProductDto dto) {
        if(dto == null){
            return;
        }
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.shortDescription = dto.getShortDescription();
        this.price = dto.getPrice();
        this.status = dto.getStatus();
        this.files= dto.getFiles();
        this.attributes = dto.getAttributes();
        this.categories = dto.getCategories();

    }
}
