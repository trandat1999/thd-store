package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductSearch;
import com.thd.store.dto.product.ProductShowDto;
import com.thd.store.service.ProductShowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Datnuclear 24/07/2024 16:34
 * @project thd-store
 * @package com.thd.store.rest
 **/
@RestController
@RequestMapping("/api/v1/product-shows")
@AllArgsConstructor
public class ProductShowController {
    private final ProductShowService productShowService;

    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody ProductShowDto request) {
        return ResponseEntity.ok(productShowService.saveOrUpdate(request, request.getId()));
    }

    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> search(@RequestBody ProductSearch request) {
        return ResponseEntity.ok(productShowService.search(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productShowService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productShowService.deleteById(id));
    }

    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@RequestBody ProductShowDto request, @PathVariable("id") Long id) {
        return ResponseEntity.ok(productShowService.saveOrUpdate(request, id));
    }
}
