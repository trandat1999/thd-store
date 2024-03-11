package com.thd.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "tbl_file")
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private Long size;
    @Column(name = "description")
    private String description;
    @Column(name = "path")
    private String path;
}
