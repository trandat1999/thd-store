package com.thd.store.repository;

import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.warehouse.WarehouseDto;
import com.thd.store.entity.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 18/04/2024
 * @project store
 */
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    @Query(value = "select count(1) from Warehouse entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.warehouse.WarehouseDto(entity) " +
            "from Warehouse entity where entity.voided <> true ")
    List<WarehouseDto> getAll();

    @Query(value = "select new com.thd.store.dto.warehouse.WarehouseDto(entity) from Warehouse entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') ) " +
            "and (:provinceId is null or entity.province.id = :provinceId)" +
            "and (:districtId is null or entity.district.id = :districtId)" +
            "and (:communeId is null or entity.commune.id = :communeId)" +
            "order by entity.lastModifiedDate desc, entity.code ")
    Page<WarehouseDto> search(String keyword, Boolean voided,Long provinceId,Long districtId,Long communeId, Pageable pageable);
}
