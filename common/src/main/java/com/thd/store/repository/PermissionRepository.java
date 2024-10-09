package com.thd.store.repository;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.user.PermissionDto;
import com.thd.store.entity.Permission;
import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 5:15 PM 9/24/2024
 * @project thd-store
 * @package com.thd.store.repository
 */
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    @Query(value = "select new com.thd.store.dto.user.PermissionDto(entity) from Permission entity where entity.voided != true")
    List<PermissionDto> getAll();

    @Query("select count(1) from Permission entity where (:id is null or entity.id != :id) and entity.module = :module and entity.action =:action")
    long countExistByModuleAndAction(PermissionType action,ApplicationModule module, Long id);

    @Query("select new com.thd.store.dto.user.PermissionDto(entity) from Permission entity " +
            "where (:voided is null or entity.voided = :voided) " +
            "and (:module is null or entity.module = :module) " +
            "and (:action is null or entity.action = :action)")
    Page<PermissionDto> getPages(ApplicationModule module, PermissionType action,Boolean voided, Pageable pageable);
}
