package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.user.PermissionDto;
import com.thd.store.repository.PermissionRepository;
import com.thd.store.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public BaseResponse getById(Long id) {
        return null;
    }

    @Override
    public BaseResponse saveOrUpdate(PermissionDto dto, Long id) {
        return null;
    }

    @Override
    public BaseResponse deleteById(Long id) {
        return null;
    }

    @Override
    public BaseResponse search(SearchRequest search) {
        return null;
    }
}
