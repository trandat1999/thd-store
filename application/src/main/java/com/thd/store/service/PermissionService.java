package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.user.PermissionDto;
import com.thd.store.dto.user.PermissionSearch;

/**
 * @author DatNuclear 5:16 PM 9/24/2024
 * @project thd-store
 * @package com.thd.store.service
 */
public interface PermissionService {
    BaseResponse getAll();
    BaseResponse getById(Long id);
    BaseResponse saveOrUpdate(PermissionDto dto, Long id);
    BaseResponse deleteById(Long id);
    BaseResponse search(PermissionSearch search);
    BaseResponse getAllApplicationModule();
}
