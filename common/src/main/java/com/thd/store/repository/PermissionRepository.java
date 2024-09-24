package com.thd.store.repository;

import com.thd.store.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author DatNuclear 5:15 PM 9/24/2024
 * @project thd-store
 * @package com.thd.store.repository
 */
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
