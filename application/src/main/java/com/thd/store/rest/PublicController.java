package com.thd.store.rest;

import com.thd.store.dto.product.ProductSearch;
import com.thd.store.elasticsearch.service.ProductEsService;
import com.thd.store.service.FileService;
import com.thd.store.dto.file.FileDto;
import com.thd.store.dto.BaseResponse;
import com.thd.store.service.ProductShowService;
import com.thd.store.util.anotation.LogActivity;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 17/01/2024
 * @project store-movie
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/publish")
public class PublicController {
    private final FileService fileService;
    private final ProductShowService productShowService;
    private final ProductEsService productEsService;

    @LogActivity
    @GetMapping(value = "/files/{id}")
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
    @LogActivity
    @PostMapping("/products")
    public BaseResponse post(@RequestBody ProductSearch search) {
        return productShowService.searchPublic(search);
    }
    @LogActivity
    @PostMapping("/es-products")
    public BaseResponse esProducts(@RequestBody ProductSearch search) {
        return productEsService.search(search);
    }
}
