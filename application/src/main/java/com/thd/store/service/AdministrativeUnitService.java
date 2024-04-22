package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
public interface AdministrativeUnitService {
    BaseResponse getAllByParentId(Long parentId);
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(AdministrativeUnitDto request, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse importExcel(MultipartFile file);
}
