package com.thd.store.repository;

import com.thd.store.dto.supplier.SupplierDto;
import com.thd.store.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 21/03/2024
 * @project store
 */
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    @Query(value = "select count(1) from Supplier entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.supplier.SupplierDto(entity) " +
            "from Supplier entity where entity.voided <> true ")
    List<SupplierDto> getAll();

    @Query(value = "select new com.thd.store.dto.supplier.SupplierDto(entity,true) from Supplier entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') ) ")
    Page<SupplierDto> search(String keyword, Boolean voided, Pageable pageable);
}
