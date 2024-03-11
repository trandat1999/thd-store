package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
public interface FileService {
    BaseResponse save(MultipartFile multipartFile);
    BaseResponse getById(Long id);
    Resource getFileByPath(String path);
}
