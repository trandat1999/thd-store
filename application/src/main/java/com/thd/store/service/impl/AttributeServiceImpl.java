package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.product.AttributeDto;
import com.thd.store.entity.Attribute;
import com.thd.store.repository.AttributeRepository;
import com.thd.store.service.AttributeService;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DatNuclear 08/03/2024
 * @project store
 */
@Service
@AllArgsConstructor
public class AttributeServiceImpl extends BaseService implements AttributeService {
    private final AttributeRepository attributeRepository;
    @Override
    public BaseResponse getAll() {
        return getResponse200(attributeRepository.getAll(),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Attribute> AttributeOptional = attributeRepository.findById(id);
        if (AttributeOptional.isPresent()){
            return getResponse200(new AttributeDto(AttributeOptional.get()),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.ATTRIBUTE));
    }

    @Override
    public BaseResponse saveOrUpdate(AttributeDto request, Long id) {
        var validator = validation(request);
        if(!validator.isEmpty()){
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        var checkCode = attributeRepository.countExistByCode(request.getCode(),id);
        if(checkCode>0){
            validator.put(SystemVariable.CODE,getMessage(SystemMessage.VALUE_EXIST,request.getCode()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        Attribute entity = null;
        if(id!=null){
            Optional<Attribute> AttributeOptional = attributeRepository.findById(id);
            if (AttributeOptional.isEmpty()){
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.ATTRIBUTE));
            }
            entity = AttributeOptional.get();
        }
        if(entity==null){
            entity = new Attribute();
        }
        entity.setVoided(request.getVoided());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity = attributeRepository.save(entity);
        return getResponse200(new AttributeDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        Optional<Attribute> AttributeOptional = attributeRepository.findById(id);
        if (AttributeOptional.isEmpty()){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.ATTRIBUTE));

        }
        var entity = AttributeOptional.get();
        entity.setVoided(true);
        entity = attributeRepository.save(entity);
        return getResponse200(new AttributeDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse search(SearchRequest search) {
        if(search.getVoided()!=null && !search.getVoided()){
            search.setVoided(null);
        }
        return getResponse200(attributeRepository.search(search.getKeyword(),search.getVoided(),getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
