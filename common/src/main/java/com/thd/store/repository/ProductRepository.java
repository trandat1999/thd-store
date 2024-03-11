package com.thd.store.repository;

import com.thd.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
}
