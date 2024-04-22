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
@Table(name = "tbl_administrative_unit")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AdministrativeUnit extends BaseInformation{
    @ManyToOne
    @JoinColumn(name="parent_id")
    private AdministrativeUnit parent;
    @Column(name="short_name")
    private String shortName;
    @Column(name="level")
    private Integer level;
}
