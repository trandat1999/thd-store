package com.thd.store.service.impl;

import com.thd.store.service.CategoryService;
import com.thd.store.dto.category.CategoryDto;
import com.thd.store.dto.category.CategorySearch;
import com.thd.store.dto.BaseResponse;
import com.thd.store.entity.Category;
import com.thd.store.repository.CategoryRepository;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DatNuclear 05/02/2024
 * @project store-movie
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends BaseService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public BaseResponse getAll() {
        return getResponse200(categoryRepository.getAll(),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()){
            return getResponse200(new CategoryDto(categoryOptional.get()),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.CATEGORY));
    }

    @Override
    public BaseResponse saveOrUpdate(CategoryDto request, Long id) {
        var validator = validation(request);
        if(!validator.isEmpty()){
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        var checkCode = categoryRepository.countExistByCode(request.getCode(),id);
        if(checkCode>0){
            validator.put(SystemVariable.CODE,getMessage(SystemMessage.VALUE_EXIST,request.getCode()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        Category entity = null;
        if(id!=null){
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            if (categoryOptional.isEmpty()){
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.CATEGORY));
            }
            entity = categoryOptional.get();
        }
        if(entity==null){
           entity = new Category();
        }
        entity.setVoided(request.getVoided());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity = categoryRepository.save(entity);
        return getResponse200(new CategoryDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.CATEGORY));

        }
        var entity = categoryOptional.get();
        entity.setVoided(true);
        entity = categoryRepository.save(entity);
        return getResponse200(new CategoryDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse search(CategorySearch search) {
        if(search.getVoided()!=null && !search.getVoided()){
            search.setVoided(null);
        }
        return getResponse200(categoryRepository.search(search.getKeyword(),search.getVoided(),getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
