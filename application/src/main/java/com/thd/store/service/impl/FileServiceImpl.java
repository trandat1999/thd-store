package com.thd.store.service.impl;

import com.thd.store.service.FileService;
import com.thd.store.dto.file.FileDto;
import com.thd.store.dto.BaseResponse;
import com.thd.store.entity.File;
import com.thd.store.exception.StoreException;
import com.thd.store.repository.FileRepository;
import com.thd.store.util.ConstUtil;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends BaseService implements FileService {
    private final FileRepository fileRepository;

    @Override
    public BaseResponse save(MultipartFile multipartFile) {
        String randomUUID = UUID.randomUUID().toString();
        String filename = multipartFile.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            return getResponse400(getMessage(SystemMessage.FILE_NAME_INVALID));
        }
        int dot = filename.lastIndexOf(ConstUtil.DOT);
        String firstString = filename.substring(0, dot);
        String fileExtension = filename.substring(dot);
        String filePath = firstString + ConstUtil.DASH + randomUUID + fileExtension;
        filePath = ConstUtil.FILE_PATH_IMAGE + filePath;
        try {
            FileOutputStream stream = new FileOutputStream(filePath);
            stream.write(multipartFile.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new StoreException(getMessage(SystemMessage.WRITE_FILE_ERROR));
        }
        File file = File.builder()
                .size(multipartFile.getSize())
                .name(multipartFile.getOriginalFilename())
                .path(filePath)
                .description(multipartFile.getContentType())
                .build();
        file = fileRepository.save(file);
        return getResponse200(new FileDto(file), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            return getResponse200(new FileDto(fileOptional.get()), getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.CATEGORY));
    }

    @Override
    public Resource getFileByPath(String path) {
        try {
            java.io.File file = new java.io.File(path);
            return new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
