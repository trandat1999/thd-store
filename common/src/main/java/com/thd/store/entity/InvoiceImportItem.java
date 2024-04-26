package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
@Table(name = "tbl_invoice_import_item")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceImportItem extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="price")
    private Double price;
    @ManyToOne
    @JoinColumn(name="invoice_import_id")
    private InvoiceImport invoice;

    public InvoiceImportItem(Product product, Integer quantity, Double price, InvoiceImport invoice) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.invoice = invoice;
    }
}
