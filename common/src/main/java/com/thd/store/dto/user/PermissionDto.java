package com.thd.store.dto.user;

import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.thd.store.entity.Permission}
 */
@Value
public class PermissionDto implements Serializable {
    Long id;
    Boolean voided;
    @NotNull(message = "{store.validation.NotNull}")
    ApplicationModule module;
    @NotNull(message = "{store.validation.NotNull}")
    PermissionType action;
}