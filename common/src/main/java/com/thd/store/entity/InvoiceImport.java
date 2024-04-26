package com.thd.store.entity;

import com.thd.store.type.InvoiceImportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Table(name = "tbl_invoice_import")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceImport extends BaseEntity{
    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private InvoiceImportType status;
    @ManyToOne
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;
    @Column(name="import_date")
    private Date importDate;
    @Column(name="code")
    private String code;
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<InvoiceImportItem> items = new ArrayList<>();
    @Column(name="total")
    private Double total;
    @Column(name="note")
    private String note;
}
