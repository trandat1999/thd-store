package com.thd.store.repository;

import com.thd.store.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
