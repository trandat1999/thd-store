package com.thd.store.repository;

import com.thd.store.dto.invoice.SaleInvoiceDto;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.entity.SaleInvoice;
import com.thd.store.type.InvoiceImportType;
import com.thd.store.type.SalesInvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

/**
 * @author DatNuclear 02/05/2024
 * @project store
 */
public interface SaleInvoiceRepository extends JpaRepository<SaleInvoice,Long> {
    @Query(value = "select new com.thd.store.dto.invoice.SaleInvoiceDto(entity) from SaleInvoice entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%')) " +
            "and (:status is null or entity.status =:status)" +
            "and (:fromDate is null or entity.saleDate >= :fromDate)" +
            "and (:toDate is null or entity.saleDate <= :toDate)")
    Page<SaleInvoiceDto> search(String keyword, Boolean voided, SalesInvoiceStatus status, Date fromDate, Date toDate, Pageable pageable);

    Optional<SaleInvoice> findByCode(String code);
}
