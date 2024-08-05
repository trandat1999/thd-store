package com.thd.store.elasticsearch.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.product.ProductSearch;
import com.thd.store.elasticsearch.dto.ProductES;
import com.thd.store.elasticsearch.repository.ProductEsRepository;
import com.thd.store.service.impl.BaseService;
import com.thd.store.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Datnuclear 01/08/2024 14:50
 * @project thd-store
 * @package com.thd.store.elasticsearch.service
 **/
@Service
@AllArgsConstructor
public class ProductEsService extends BaseService {
    private final ProductEsRepository productEsRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;


    public BaseResponse saveOrUpdate(ProductES product) {
        return getResponse200(productEsRepository.save(product), getMessage(SystemMessage.SUCCESS));
    }

    public BaseResponse search(ProductSearch search) {
        return getResponse200(productEsRepository.findByNameLike(search.getKeyword() == null ? "" : search.getKeyword(), getPageableES(search)), getMessage(SystemMessage.SUCCESS));
    }
}
