package com.thd.store.dto.invoice;

import com.thd.store.dto.BaseDto;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.entity.InvoiceImportItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class InvoiceImportItemDto extends BaseDto {
    @NotNull(message = "{store.validation.NotNull}")
    private ProductDto product;
    @NotNull(message = "{store.validation.NotNull}")
    @Min(value = 1, message = "{store.validation.Min}")
    private Integer quantity;
    @NotNull(message = "{store.validation.NotNull}")
    @Min(value = 0, message = "{store.validation.Min}")
    private Double price;

    public InvoiceImportItemDto(InvoiceImportItem entity) {
        if(entity!=null) {
            this.quantity = entity.getQuantity();
            this.price = entity.getPrice();
            this.product = new ProductDto(entity.getProduct());
        }
    }
}
