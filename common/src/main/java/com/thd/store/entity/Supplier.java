package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author DatNuclear 07/03/2024
 * @project store
 */
@Table(name = "tbl_supplier")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseInformation{
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(orphanRemoval = true,mappedBy = "supplier",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<SupplierProduct> products = new HashSet<>();
}
