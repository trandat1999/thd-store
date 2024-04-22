package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.administrativeUnit.AdministrativeUnitDto;
import com.thd.store.service.AdministrativeUnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author DatNuclear 17/04/2024
 * @project store
 */
@RestController
@RequestMapping(value = "/api/v1/administrative-units")
@AllArgsConstructor
public class AdministrativeUnitController {
    private final AdministrativeUnitService administrativeUnitService;
    private final Object lock = new Object();
    @GetMapping(value = "/all-province")
    public BaseResponse getAllProvince(){
        return administrativeUnitService.getAllByParentId(null);
    }
    @GetMapping(value = "/all-by-parent/{id}")
    public BaseResponse getAllByParentId(@PathVariable Long id){
        return administrativeUnitService.getAllByParentId(id);
    }
    @PostMapping(value = "/import-excel")
    public BaseResponse importExcel(@RequestParam("file")MultipartFile file){
        synchronized (lock) {
            return administrativeUnitService.importExcel(file);
        }
    }
    @PostMapping
    public BaseResponse save(@RequestBody AdministrativeUnitDto request){
        return administrativeUnitService.saveOrUpdate(request, request.getId());
    }
    @GetMapping(value = "/{id}")
    public BaseResponse get(@PathVariable Long id){
        return administrativeUnitService.getById(id);
    }
    @DeleteMapping(value = "/{id}")
    public BaseResponse delete(@PathVariable Long id){
        return administrativeUnitService.deleteById(id);
    }
    @PutMapping(value = "/{id}")
    public BaseResponse put(@PathVariable Long id,@RequestBody AdministrativeUnitDto request){
        return administrativeUnitService.saveOrUpdate(request, id);
    }
}
