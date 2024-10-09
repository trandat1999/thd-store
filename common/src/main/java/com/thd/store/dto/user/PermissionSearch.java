package com.thd.store.dto.user;

import com.thd.store.dto.SearchRequest;
import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import lombok.Data;

/**
 * @author DatNuclear 8:58 AM 9/25/2024
 * @project thd-store
 * @package com.thd.store.dto.user
 */
@Data
public class PermissionSearch extends SearchRequest {
    private ApplicationModule module;
    private PermissionType action;
}
