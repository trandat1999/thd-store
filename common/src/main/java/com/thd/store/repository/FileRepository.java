package com.thd.store.repository;

import com.thd.store.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
public interface FileRepository extends JpaRepository<File,Long> {
}
