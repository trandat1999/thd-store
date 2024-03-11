package com.thd.store.rest;

import com.thd.store.service.CategoryService;
import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.category.CategorySearch;
import com.thd.store.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 05/02/2024
 * @project store-movie
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody CategoryDto request){
        return ResponseEntity.ok(categoryService.saveOrUpdate(request,request.getId()));
    }
    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> search(@RequestBody CategorySearch request){
        return ResponseEntity.ok(categoryService.search(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> search(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.getById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.deleteById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@RequestBody CategoryDto request,@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.saveOrUpdate(request,id));
    }
}
