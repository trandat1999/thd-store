package com.thd.store.service;

import com.thd.store.dto.administrativeUnit.AdministrativeUnitImport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
public interface ExcelService {
    List<AdministrativeUnitImport> readAdministrativeUnit(MultipartFile file);
}
