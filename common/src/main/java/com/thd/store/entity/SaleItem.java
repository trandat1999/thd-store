package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
@Table(name = "tbl_sale_item")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    @JoinColumn(name="sale_invoice_id")
    private SaleInvoice saleInvoice;
}
