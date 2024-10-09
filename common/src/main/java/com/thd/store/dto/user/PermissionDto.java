package com.thd.store.dto.user;

import com.thd.store.entity.Permission;
import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * DTO for {@link com.thd.store.entity.Permission}
 */
@Data
@NoArgsConstructor
public class PermissionDto implements Serializable {
    private Long id;
    private Boolean voided;
    @NotNull(message = "{store.validation.NotNull}")
    private ApplicationModule module;
    @NotNull(message = "{store.validation.NotNull}")
    private PermissionType action;

    public PermissionDto(Permission entity) {
        if(entity!=null){
            this.id = entity.getId();
            this.voided = entity.getVoided();
            this.module = entity.getModule();
            this.action = entity.getAction();
        }
    }
}