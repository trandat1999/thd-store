package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 28/04/2024
 * @project store
 */
@Table(name = "tbl_inventory")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Inventory extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "invoice_import_id")
    private InvoiceImport invoiceImport;
    @ManyToOne
    @JoinColumn(name = "sales_invoice_id")
    private SaleInvoice saleInvoice;
    @Column(name = "status",columnDefinition = "int check (status in (1,-1))")
    private Integer status;
}
