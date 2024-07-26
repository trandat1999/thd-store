package com.thd.store.repository;

import com.thd.store.dto.product.ProductShowDto;
import com.thd.store.entity.ProductShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductShowRepository extends JpaRepository<ProductShow,Long> {
    Optional<ProductShow> findByProductId(Long productId);

    @Query(value = "select new com.thd.store.dto.product.ProductShowDto(entity) from ProductShow entity " +
            "where (:keyword is null or :keyword = '' or entity.product.name like concat('%',:keyword,'%') " +
            "or entity.product.code like concat('%',:keyword,'%') ) " +
            "and (:status is null or entity.status = :status) " +
            "and (:priceFrom is null or entity.price>= :priceFrom) " +
            "and (:priceTo is null or entity.price <= :priceTo) ")
    Page<ProductShowDto> search(String keyword, Integer status, Double priceFrom, Double priceTo, Pageable pageable);
}
