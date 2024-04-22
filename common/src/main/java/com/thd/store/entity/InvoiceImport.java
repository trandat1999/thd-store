package com.thd.store.entity;

import com.thd.store.type.InvoiceImportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

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
public class InvoiceImport extends BaseInformation{
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
    @Column(name="uuid_code")
    private UUID uuidCode;
}
