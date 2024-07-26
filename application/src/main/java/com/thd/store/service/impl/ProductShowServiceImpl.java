package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductSearch;
import com.thd.store.dto.product.ProductShowDto;
import com.thd.store.entity.Product;
import com.thd.store.entity.ProductShow;
import com.thd.store.repository.ProductRepository;
import com.thd.store.repository.ProductShowRepository;
import com.thd.store.service.ProductShowService;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductShowServiceImpl extends BaseService implements ProductShowService {
    private final ProductShowRepository productShowRepository;
    private final ProductRepository productRepository;

    @Override
    public BaseResponse getById(Long id) {
        var optional = productShowRepository.findById(id);
        if (optional.isPresent()) {
            return getResponse200(new ProductShowDto(optional.get()), getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PRODUCT)));
    }

    @Override
    public BaseResponse saveOrUpdate(ProductShowDto request, Long id) {
        var validator = validation(request);
        if (!validator.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validator);
        }
        ProductShow entity = null;
        if (id != null) {
            entity = productShowRepository.findById(id).orElse(null);
        }
        if (entity == null && request.getProduct().getId() != null) {
            entity = productShowRepository.findByProductId(request.getProduct().getId()).orElse(null);
        }
        if (entity == null) {
            entity = new ProductShow();
        }
        entity.setPrice(request.getPrice());
        entity.setStatus(request.getStatus());
        Product product = null;
        if (request.getProduct().getId() != null) {
            product = productRepository.findById(request.getProduct().getId()).orElse(null);
            if (product == null) {
                return getResponse400(getMessage(SystemMessage.NOT_FOUND, getMessage(SystemVariable.PRODUCT)));
            }
        }
        entity.setProduct(product);
        entity = productShowRepository.save(entity);
        return getResponse200(new ProductShowDto(entity), getMessage(SystemMessage.SUCCESS));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        return null;
    }

    @Override
    public BaseResponse search(ProductSearch search) {
        return getResponse200(productShowRepository.search(search.getKeyword(), search.getStatus(), search.getPriceFrom(),
                        search.getPriceTo(), getPageable(search)),
                getMessage(SystemMessage.SUCCESS));
    }
}
