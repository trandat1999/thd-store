package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@Table(name = "tbl_warehouse")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Warehouse extends BaseInformation{
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
}
