package com.thd.store.service;

import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.SearchRequest;
import com.thd.store.dto.customer.CustomerDto;

/**
 * @author Datnuclear 30/07/2024 13:51
 * @project thd-store
 * @package com.thd.store.service
 **/
public interface PersonService {
    BaseResponse saveOrUpdate(CustomerDto request, Long id);
    BaseResponse getPages(SearchRequest search);
}
