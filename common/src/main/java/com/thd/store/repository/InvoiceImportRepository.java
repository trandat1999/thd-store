package com.thd.store.repository;

import com.thd.store.dto.product.ProductDto;
import com.thd.store.entity.InvoiceImport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
public interface InvoiceImportRepository extends JpaRepository<InvoiceImport,Long> {
    @Query(value = "select count(1) from InvoiceImport entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.product.ProductDto(entity) " +
            "from Product entity where entity.voided <> true ")
    List<ProductDto> getAll();

    @Query(value = "select new com.thd.store.dto.product.ProductDto(entity) from Product entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') ) " +
            "order by entity.code ")
    Page<ProductDto> search(String keyword, Boolean voided, Pageable pageable);
}
