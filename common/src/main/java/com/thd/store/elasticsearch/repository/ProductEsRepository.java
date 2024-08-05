package com.thd.store.elasticsearch.repository;

import com.thd.store.elasticsearch.dto.ProductES;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Datnuclear 01/08/2024 14:49
 * @project thd-store
 * @package com.thd.store.elasticsearch.repository
 **/
public interface ProductEsRepository extends ElasticsearchRepository<ProductES,Long> {
    Page<ProductES> findByNameLike(String name,Pageable pageable);
}
