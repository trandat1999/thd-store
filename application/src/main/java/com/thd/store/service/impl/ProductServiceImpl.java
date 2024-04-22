package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.dto.product.ProductSearch;
import com.thd.store.entity.File;
import com.thd.store.entity.Product;
import com.thd.store.entity.ProductFile;
import com.thd.store.repository.FileRepository;
import com.thd.store.repository.ProductRepository;
import com.thd.store.service.ProductService;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

/**
 * @author DatNuclear 05/04/2024
 * @project store
 */
@Service
@CacheConfig(cacheNames = "products")
@AllArgsConstructor
public class ProductServiceImpl extends BaseService implements ProductService {
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;
    @Override
    @Cacheable
    public BaseResponse getAll() {
        return getResponse200(productRepository.getAll(),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()){
            return getResponse200(new ProductDto(optional.get(),true,true),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PRODUCT) ));
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(ProductDto request, Long id) {
        var validator = validation(request);
        if(!validator.isEmpty()){
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        var checkCode = productRepository.countExistByCode(request.getCode(),id);
        if(checkCode>0){
            validator.put(SystemVariable.CODE,getMessage(SystemMessage.VALUE_EXIST,request.getCode()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        Product entity = null;
        if(id!=null){
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()){
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, SystemVariable.CATEGORY));
            }
            entity = optional.get();
        }
        if(entity==null){
            entity = new Product();
        }
        entity.getFiles().clear();
        if(!CollectionUtils.isEmpty(request.getFiles())){
            for(var fileDto : request.getFiles()){
                if(fileDto.getId()!=null){
                    File file = fileRepository.findById(fileDto.getId()).orElse(null);
                    if(file!=null){
                        entity.getFiles().add(new ProductFile(entity,file,1));
                    }
                }
            }
        }
        entity.getAttributes().clear();
        if(!CollectionUtils.isEmpty(request.getAttributes())){
            for(var attribute : request.getAttributes()){
                entity.getAttributes().put(attribute.getKey(),attribute.getValue());
            }
        }
        entity.setVoided(request.getVoided());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setShortDescription(request.getShortDescription());
        entity = productRepository.save(entity);
        return getResponse200(new ProductDto(entity,true,true),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse deleteById(Long id) {
        var optional = productRepository.findById(id);
        if (optional.isEmpty()){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PRODUCT)));

        }
        var entity = optional.get();
        entity.setVoided(true);
        entity = productRepository.save(entity);
        return getResponse200(new ProductDto(entity,true,true),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @Cacheable(key = "#search.hashCode()")
    public BaseResponse search(ProductSearch search) {
        if(search.getVoided()!=null && !search.getVoided()){
            search.setVoided(null);
        }
        return getResponse200(productRepository.search(search.getKeyword(),search.getVoided(),getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
