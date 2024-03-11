package com.thd.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DatNuclear 07/03/2024
 * @project store
 */
@Table(name = "tbl_product")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Product extends BaseInformation{
    @OneToMany(mappedBy = "product",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductFile> files;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="product_attributes", joinColumns=@JoinColumn(name="product_id"))
    private Map<String, String> attributes = new HashMap<>();
}
