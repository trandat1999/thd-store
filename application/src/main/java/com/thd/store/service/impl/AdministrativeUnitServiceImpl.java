package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.ImportExcelResponse;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitImport;
import com.thd.store.entity.AdministrativeUnit;
import com.thd.store.repository.AdministrativeUnitRepository;
import com.thd.store.service.AdministrativeUnitService;
import com.thd.store.service.ExcelService;
import com.thd.store.util.SystemMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@AllArgsConstructor
@Service
@CacheConfig(cacheNames = {"administrativeUnit"})
public class AdministrativeUnitServiceImpl extends BaseService implements AdministrativeUnitService {
    private final AdministrativeUnitRepository administrativeUnitRepository;
    private final ExcelService excelService;
    @Override
    @Cacheable(key = "#parentId==null?0:#parentId")
    public BaseResponse getAllByParentId(Long parentId) {
        return getResponse200(administrativeUnitRepository.getAllByParentId(parentId),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        return null;
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(AdministrativeUnitDto request, Long id) {
        return null;
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse deleteById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponse importExcel(MultipartFile file) {
        List<AdministrativeUnitImport> administrativeUnitImports = excelService.readAdministrativeUnit(file);
        List<AdministrativeUnitImport> success = new ArrayList<>();
        List<AdministrativeUnitImport> fails = new ArrayList<>();
        Optional<AdministrativeUnit> optional;
        AdministrativeUnit entity;
        for(var item : administrativeUnitImports){
            if(!item.isValid()){
                fails.add(item);
                continue;
            }
            optional = administrativeUnitRepository.findByCode(item.getCode());
            if(optional.isPresent()){
                entity = optional.get();
            }else{
                entity = new AdministrativeUnit();
                entity.setCode(item.getCode());
            }
            if(StringUtils.hasText(item.getParentCode())){
                optional = administrativeUnitRepository.findByCode(item.getParentCode());
                if(optional.isPresent()){
                    entity.setParent(optional.get());
                }else{
                    fails.add(item);
                    continue;
                }
            }
            entity.setName(item.getName());
            entity.setLevel(item.getLevel());
            administrativeUnitRepository.save(entity);
            success.add(item);
        }
        ImportExcelResponse rs = ImportExcelResponse.builder()
                .total((long)administrativeUnitImports.size())
                .success((long)success.size())
                .failure((long)fails.size())
                .failureList(fails)
                .successList(success)
                .build();
        return getResponse200(rs,getMessage(SystemMessage.SUCCESS));
    }
}
