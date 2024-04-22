package com.thd.store.dto.user;

import com.thd.store.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 24/03/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String name;
    public RoleDto(Role entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.name = entity.getName();
        }
    }
}
