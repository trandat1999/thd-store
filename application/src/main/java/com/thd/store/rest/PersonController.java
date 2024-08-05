package com.thd.store.rest;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.customer.CustomerDto;
import com.thd.store.service.PersonService;
import com.thd.store.util.anotation.LogActivity;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Datnuclear 30/07/2024 14:01
 * @project thd-store
 * @package com.thd.store.rest
 **/
@RestController
@RequestMapping("/api/v1/persons")
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority(T(com.thd.store.util.ConstUtil).ADMIN_ROLE)")
public class PersonController {
    private final PersonService personService;
    @PostMapping("")
    @LogActivity()
    public BaseResponse post(@RequestBody CustomerDto request) {
        return personService.saveOrUpdate(request,request.getId());
    }
    @PostMapping("/pages")
    @LogActivity
    public BaseResponse post(@RequestBody SearchRequest search) {
        return personService.getPages(search);
    }
}
