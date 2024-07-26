package com.thd.store.repository;

import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.product.AttributeDto;
import com.thd.store.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
public interface AttributeRepository extends JpaRepository<Attribute,Long> {
    @Query(value = "select count(1) from Attribute entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.product.AttributeDto(entity) " +
            "from Attribute entity where entity.voided <> true order by entity.name")
    List<AttributeDto> getAll();

    @Query(value = "select new com.thd.store.dto.product.AttributeDto(entity) from Attribute entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') ) " +
            "order by entity.lastModifiedDate desc, entity.code ")
    Page<AttributeDto> search(String keyword, Boolean voided, Pageable pageable);
}
