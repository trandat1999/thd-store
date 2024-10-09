package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.user.PermissionDto;
import com.thd.store.dto.user.PermissionSearch;
import com.thd.store.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author DatNuclear 9:05 AM 9/25/2024
 * @project thd-store
 * @package com.thd.store.rest
 */
@RestController
@RequestMapping(value = "/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(permissionService.getAll());
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<BaseResponse> save(@RequestBody PermissionDto request){
        return ResponseEntity.ok(permissionService.saveOrUpdate(request,request.getId()));
    }
    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> search(@RequestBody PermissionSearch request){
        return ResponseEntity.ok(permissionService.search(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(permissionService.getById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(permissionService.deleteById(id));
    }
    @PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> put(@RequestBody PermissionDto request,@PathVariable("id") Long id){
        return ResponseEntity.ok(permissionService.saveOrUpdate(request,id));
    }
    @GetMapping("/all-module")
    public BaseResponse getAllModule(){
        return permissionService.getAllApplicationModule();
    }
}
