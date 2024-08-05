package com.thd.store.entity;

import com.thd.store.type.PaymentType;
import com.thd.store.type.SalesInvoiceStatus;
import com.thd.store.type.SalesInvoiceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DatNuclear 28/04/2024
 * @project store
 */
@Table(name = "tbl_sale_invoice")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SaleInvoice extends BaseEntity {
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SalesInvoiceType type;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private AdministrativeUnit province;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private AdministrativeUnit district;
    @ManyToOne
    @JoinColumn(name = "commune_id")
    private AdministrativeUnit commune;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "total")
    private Double total;
    @Column(name = "is_paid")
    private Boolean paid;
    @Column(name = "sale_date")
    private Date saleDate;
    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SalesInvoiceStatus status;
    @Column(name="code")
    private String code;
    @Column(name="note")
    private String note;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "saleInvoice")
    private List<SaleItem> items = new ArrayList<>();
}
