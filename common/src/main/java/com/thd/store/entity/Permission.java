package com.thd.store.entity;

import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 23/09/2024 9:21 CH
 * @project thd-store
 * @package com.thd.store.entity
 */
@Table(name = "tbl_permission")
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Permission extends BaseEntity{
    @Column(name = "module", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationModule module;
    @Column(name = "action",nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionType action;
}
