package com.thd.store.rest;

import com.thd.store.service.FileService;
import com.thd.store.dto.file.FileDto;
import com.thd.store.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
@RestController
@RequestMapping(value = "/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(fileService.save(multipartFile));
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id){
        BaseResponse response = fileService.getById(id);
        if(response.getBody() != null){
            FileDto fileResponse = (FileDto) response.getBody();
            Resource resource = fileService.getFileByPath(fileResponse.getPath());
            if(ObjectUtils.isEmpty(resource)){
                return ResponseEntity.badRequest().build();
            }
            String headerValue = "attachment; filename=\"" + fileResponse.getName() + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .contentLength(fileResponse.getSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.ok(response);
    }
}
