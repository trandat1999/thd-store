package com.thd.store.repository;

import com.thd.store.dto.category.CategoryDto;
import com.thd.store.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DatNuclear 03/02/2024
 * @project store-movie
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select count(1) from Category entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.category.CategoryDto(entity) " +
            "from Category entity where entity.voided <> true")
    List<CategoryDto> getAll();

    @Query(value = "select new com.thd.store.dto.category.CategoryDto(entity) from Category entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') ) " +
            "order by entity.lastModifiedDate desc, entity.code ")
    Page<CategoryDto> search(String keyword, Boolean voided, Pageable pageable);

}
