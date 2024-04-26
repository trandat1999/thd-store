package com.thd.store.repository;

import com.thd.store.dto.invoice.InvoiceImportDto;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.type.InvoiceImportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

/**
 * @author DatNuclear 20/04/2024
 * @project store
 */
public interface InvoiceImportRepository extends JpaRepository<InvoiceImport,Long> {
    @Query(value = "select count(1) from InvoiceImport entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.invoice.InvoiceImportDto(entity) from InvoiceImport entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%')) " +
            "and (:status is null or entity.status =:status)" +
            "and (:fromDate is null or entity.importDate >= :fromDate)" +
            "and (:toDate is null or entity.importDate <= :toDate)" +
            "order by entity.code ")
    Page<InvoiceImportDto> search(String keyword, Boolean voided, InvoiceImportType status, Date fromDate, Date toDate, Pageable pageable);

    Optional<InvoiceImport> findByCode(String code);
}
