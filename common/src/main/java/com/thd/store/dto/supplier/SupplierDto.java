package com.thd.store.dto.supplier;

import com.thd.store.dto.BaseInformationDto;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.entity.Supplier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DatNuclear 21/03/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class SupplierDto extends BaseInformationDto {
    @NotNull(message = "{store.validation.NotNull}")
    @NotBlank(message = "{store.validation.NotBlank}")
    @Email(message = "{store.validation.email}")
    private String email;
    @NotNull(message = "{store.validation.NotNull}")
    @NotBlank(message = "{store.validation.NotBlank}")
    private String phoneNumber;
    private List<ProductDto> products = new ArrayList<>();

    public SupplierDto(Supplier entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.voided = entity.getVoided();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.shortDescription = entity.getShortDescription();
            this.email = entity.getEmail();
            this.phoneNumber = entity.getPhoneNumber();
        }
    }
    public SupplierDto(Supplier entity,boolean products) {
        this(entity);
        if (entity != null) {
            if(products && !CollectionUtils.isEmpty(entity.getProducts())){
                for(var item : entity.getProducts()){
                    this.products.add(new ProductDto(item.getProduct()));
                }
            }
        }
    }
}
