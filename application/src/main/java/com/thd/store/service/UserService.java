package com.thd.store.service;

import com.thd.store.dto.BaseResponse;

/**
 * @author DatNuclear 30/01/2024
 * @project store-movie
 */
public interface UserService {
    BaseResponse getCurrentUser();
    BaseResponse getRoles();
}
