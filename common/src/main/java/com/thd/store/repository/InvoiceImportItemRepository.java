package com.thd.store.repository;

import com.thd.store.entity.InvoiceImportItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Datnuclear 26/07/2024 15:32
 * @project thd-store
 * @package com.thd.store.repository
 **/
public interface InvoiceImportItemRepository extends JpaRepository<InvoiceImportItem,Long> {
    @Query("select entity.price from InvoiceImportItem entity where entity.product.id = :productId order by entity.createdDate desc limit 1")
    Double findFirstPriceByProductIdOrderByCreatedDateDesc(Long productId);
}
