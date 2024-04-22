package com.thd.store.repository;

import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import com.thd.store.entity.AdministrativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
public interface AdministrativeUnitRepository extends JpaRepository<AdministrativeUnit, Long> {
    @Query(value = "select count(1) from AdministrativeUnit entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.administrativeUnit.AdministrativeUnitDto(entity) from AdministrativeUnit entity " +
            "where (entity.voided is null or entity.voided = false) " +
            "and ((:parentId is null and entity.parent is null) or entity.parent.id = :parentId)")
    List<AdministrativeUnitDto> getAllByParentId(Long parentId);

    Optional<AdministrativeUnit> findByCode(String code);
}
