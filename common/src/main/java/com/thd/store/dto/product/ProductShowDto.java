package com.thd.store.dto.product;

import com.thd.store.dto.BaseDto;
import com.thd.store.entity.ProductShow;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductShowDto extends BaseDto {
    @NotNull(message = "${store.validation.NotNull}")
    private Integer status;
    private Double price;
    @NotNull(message = "${store.validation.NotNull}")
    private ProductDto product;

    public ProductShowDto() {
    }
    public ProductShowDto(ProductShow entity) {
        if (entity == null) {
            return;
        }
        this.product = new ProductDto(entity.getProduct());
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.price = entity.getPrice();
    }
}
