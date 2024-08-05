package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.customer.CustomerDto;
import com.thd.store.entity.Person;
import com.thd.store.repository.PersonRepository;
import com.thd.store.service.PersonService;
import com.thd.store.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Datnuclear 30/07/2024 13:55
 * @project thd-store
 * @package com.thd.store.service.impl
 **/
@Service
@AllArgsConstructor
public class PersonServiceImpl extends BaseService implements PersonService {
    private final PersonRepository personRepository;
    @Override
    public BaseResponse saveOrUpdate(CustomerDto request, Long id) {
        Person entity = null;
        if (id!= null) {
            entity = personRepository.findById(id).orElse(null);
        }
        if (entity == null) {
            entity = new Person();
        }
        entity.setBirthDate(request.getBirthDate());
        entity.setEmail(request.getEmail());
        entity.setFirstName(request.getFirstName());
        entity.setDisplayName(request.getDisplayName());
        entity.setLastName(request.getLastName());
        entity.setGender(request.getGender());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity = personRepository.save(entity);
        return getResponse200(new CustomerDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getPages(SearchRequest search) {
        return getResponse200(personRepository.getPages(search.getKeyword(), getPageable(search)), getMessage(SystemMessage.SUCCESS));
    }
}
