package com.thd.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tbl_product_category")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
