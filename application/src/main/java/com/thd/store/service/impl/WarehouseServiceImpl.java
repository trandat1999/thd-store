package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.warehouse.WarehouseDto;
import com.thd.store.dto.warehouse.WarehouseSearch;
import com.thd.store.entity.AdministrativeUnit;
import com.thd.store.entity.Warehouse;
import com.thd.store.repository.AdministrativeUnitRepository;
import com.thd.store.repository.WarehouseRepository;
import com.thd.store.service.WarehouseService;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DatNuclear 18/04/2024
 * @project store
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames={"warehouse"})
public class WarehouseServiceImpl extends BaseService implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final AdministrativeUnitRepository administrativeUnitRepository;

    @Override
//    @Cacheable()
    public BaseResponse getAll() {
        return getResponse200(warehouseRepository.getAll(), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if (optional.isPresent()) {
            return getResponse200(new WarehouseDto(optional.get()), getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.WAREHOUSE)));
    }

    @CacheEvict(allEntries = true)
    @Override
    public BaseResponse saveOrUpdate(WarehouseDto request, Long id) {
        var validator = validation(request);
        if(request.getProvince()!=null && request.getProvince().getId()==null){
            validator.put(SystemVariable.PROVINCE_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PROVINCE)));
        }
        if(request.getDistrict()!=null && request.getDistrict().getId()==null){
            validator.put(SystemVariable.DISTRICT_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.DISTRICT)));
        }
        if(request.getCommune()!=null && request.getCommune().getId()==null){
            validator.put(SystemVariable.COMMUNE_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.COMMUNE)));
        }
        AdministrativeUnit province = administrativeUnitRepository.findById(request.getProvince().getId()).orElse(null);
        AdministrativeUnit district = administrativeUnitRepository.findById(request.getDistrict().getId()).orElse(null);
        AdministrativeUnit commune = administrativeUnitRepository.findById(request.getCommune().getId()).orElse(null);
        if(province==null){
            validator.put(SystemVariable.PROVINCE_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PROVINCE)));
        }
        if(district==null){
            validator.put(SystemVariable.DISTRICT_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.DISTRICT)));
        }
        if(commune==null){
            validator.put(SystemVariable.COMMUNE_FIELD, getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.COMMUNE)));
        }
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        var checkCode = warehouseRepository.countExistByCode(request.getCode(), id);
        if (checkCode > 0) {
            validator.put(SystemVariable.CODE, getMessage(SystemMessage.VALUE_EXIST, request.getCode()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        Warehouse entity = null;
        if (id != null) {
            Optional<Warehouse> WarehouseOptional = warehouseRepository.findById(id);
            if (WarehouseOptional.isEmpty()) {
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.WAREHOUSE)));
            }
            entity = WarehouseOptional.get();
        }
        if (entity == null) {
            entity = new Warehouse();
        }
        entity.setProvince(province);
        entity.setDistrict(district);
        entity.setCommune(commune);
        entity.setVoided(request.getVoided());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setAddress(request.getAddress());
        entity = warehouseRepository.save(entity);
        return getResponse200(new WarehouseDto(entity), getMessage(SystemMessage.SUCCESS));
    }

    @CacheEvict(allEntries = true)
    @Override
    public BaseResponse deleteById(Long id) {
        Optional<Warehouse> WarehouseOptional = warehouseRepository.findById(id);
        if (WarehouseOptional.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.WAREHOUSE)));

        }
        var entity = WarehouseOptional.get();
        entity.setVoided(true);
        entity = warehouseRepository.save(entity);
        return getResponse200(new WarehouseDto(entity), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @Cacheable(key = "#search.hashCode()")
    public BaseResponse search(WarehouseSearch search) {
        if (search.getVoided() != null && !search.getVoided()) {
            search.setVoided(null);
        }
        return getResponse200(warehouseRepository.search(search.getKeyword(), search.getVoided(),
                        search.getProvinceId(), search.getDistrictId(),search.getCommuneId(),
                        getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
