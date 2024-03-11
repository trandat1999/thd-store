package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.product.AttributeDto;
import com.thd.store.service.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attributes")
public class AttributeController {
    private final AttributeService attributeService;
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(attributeService.getAll());
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody AttributeDto request){
        return ResponseEntity.ok(attributeService.saveOrUpdate(request,request.getId()));
    }
    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> search(@RequestBody SearchRequest request){
        return ResponseEntity.ok(attributeService.search(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(attributeService.getById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(attributeService.deleteById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@RequestBody AttributeDto request,@PathVariable("id") Long id){
        return ResponseEntity.ok(attributeService.saveOrUpdate(request,id));
    }
}
