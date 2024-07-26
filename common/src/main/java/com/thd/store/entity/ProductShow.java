package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tbl_product_show")
@Entity
@SuperBuilder
@NoArgsConstructor
public class ProductShow extends BaseEntity{
    @Column(name = "price")
    private Double price;
    @Column(name = "status")
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "product_id",unique = true)
    private Product product;
}
