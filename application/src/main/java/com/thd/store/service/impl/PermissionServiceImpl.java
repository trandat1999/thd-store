package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.user.PermissionDto;
import com.thd.store.dto.user.PermissionSearch;
import com.thd.store.entity.Permission;
import com.thd.store.repository.PermissionRepository;
import com.thd.store.service.PermissionService;
import com.thd.store.type.ApplicationModule;
import com.thd.store.type.PermissionType;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DatNuclear 5:25 PM 9/24/2024
 * @project thd-store
 * @package com.thd.store.service.impl
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"permission"})
public class PermissionServiceImpl extends BaseService implements PermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public BaseResponse getAll() {
        return getResponse200(permissionRepository.getAll(), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isPresent()) {
            return getResponse200(new PermissionDto(optional.get()), getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PERMISSION)));
    }

    @Override
    public BaseResponse saveOrUpdate(PermissionDto request, Long id) {
        var validator = validation(request);
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        var checkCode = permissionRepository.countExistByModuleAndAction(request.getAction(), request.getModule(), id);
        if (checkCode > 0) {
            validator.put(SystemVariable.MODULE, getMessage(SystemMessage.VALUE_EXIST, request.getModule()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        Permission entity = null;
        if (id != null) {
            Optional<Permission> optional = permissionRepository.findById(id);
            if (optional.isEmpty()) {
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PERMISSION)));
            }
            entity = optional.get();
        }
        if (entity == null) {
            entity = new Permission();
        }
        entity.setVoided(request.getVoided());
        entity.setAction(request.getAction());
        entity.setModule(request.getModule());
        entity = permissionRepository.save(entity);
        return getResponse200(new PermissionDto(entity), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        Optional<Permission> optional = permissionRepository.findById(id);
        if (optional.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PERMISSION)));
        }
        Permission entity = optional.get();
        entity.setVoided(true);
        entity = permissionRepository.save(entity);
        return getResponse200(new PermissionDto(entity), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse search(PermissionSearch search) {
        if (search.getVoided() != null && !search.getVoided()) {
            search.setVoided(null);
        }
        return getResponse200(permissionRepository.getPages(search.getModule(), search.getAction(), search.getVoided(),
                getPageable(search)), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getAllApplicationModule() {
        return getResponse200(ApplicationModule.values(),getMessage(SystemMessage.SUCCESS));
    }
}
