package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.product.ProductDto;
import com.thd.store.dto.supplier.SupplierDto;
import com.thd.store.entity.Supplier;
import com.thd.store.entity.SupplierProduct;
import com.thd.store.repository.ProductRepository;
import com.thd.store.repository.SupplierRepository;
import com.thd.store.service.SupplierService;
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
 * @author DatNuclear 21/03/2024
 * @project store
 */
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "supplier")
public class SupplierServiceImpl extends BaseService implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    @Override
    @Cacheable()
    public BaseResponse getAll() {
        return getResponse200(supplierRepository.getAll(),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse getById(Long id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isPresent()){
            return getResponse200(new SupplierDto(supplierOptional.get(),true),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.SUPPLIER)));
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(SupplierDto request, Long id) {
        var validator = validation(request);
        if(!validator.isEmpty()){
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        var checkCode = supplierRepository.countExistByCode(request.getCode(),id);
        if(checkCode>0){
            validator.put(SystemVariable.CODE,getMessage(SystemMessage.VALUE_EXIST,request.getCode()));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST),validator);
        }
        Supplier entity = null;
        if(id!=null){
            Optional<Supplier> supplierOptional = supplierRepository.findById(id);
            if (supplierOptional.isEmpty()){
                return getResponse400(getMessage(SystemMessage.NOT_FOUND,getMessage(SystemVariable.SUPPLIER)));
            }
            entity = supplierOptional.get();
        }
        if(entity==null){
            entity = new Supplier();
        }
        entity.getProducts().clear();
        if(!CollectionUtils.isEmpty(request.getProducts())){
            for(ProductDto product : request.getProducts()){
                if(product.getId()!=null){
                    var productOptional = productRepository.findById(product.getId());
                    if(productOptional.isPresent()){
                        entity.getProducts().add(new SupplierProduct(entity,productOptional.get()));
                    }
                }
            }
        }
        entity.setVoided(request.getVoided());
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setShortDescription(request.getShortDescription());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity = supplierRepository.save(entity);
        return getResponse200(new SupplierDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @CacheEvict(allEntries = true)
    public BaseResponse deleteById(Long id) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (supplierOptional.isEmpty()){
            return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.SUPPLIER)));

        }
        var entity = supplierOptional.get();
        entity.setVoided(true);
        entity = supplierRepository.save(entity);
        return getResponse200(new SupplierDto(entity),getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @Cacheable(key = "#search.hashCode()")
    public BaseResponse search(SearchRequest search) {
        if(search.getVoided()!=null && !search.getVoided()){
            search.setVoided(null);
        }
        return getResponse200(supplierRepository.search(search.getKeyword(),search.getVoided(),getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
