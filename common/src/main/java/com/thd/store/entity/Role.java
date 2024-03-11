package com.thd.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;


@Table(name = "tbl_role")
@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "role_name", length = 150, nullable = false)
    private String name;
    @Column(name = "role_description", length = 512)
    private String description;
    @Override
    public String getAuthority() {
        return this.name;
    }
}
