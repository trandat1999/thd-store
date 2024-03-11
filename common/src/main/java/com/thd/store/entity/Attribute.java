package com.thd.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 26/01/2024
 * @project store-movie
 */
@Entity
@Table(name = "tbl_attribute")
@Data
@SuperBuilder
@NoArgsConstructor
public class Attribute extends BaseInformation{
}
