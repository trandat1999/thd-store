package com.thd.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 26/01/2024
 * @project store-movie
 */
@Entity
@Table(name = "tbl_category")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;
    @Column(name = "parent_id")
    private Long parentId;
}
