package com.thd.store.repository;

import com.thd.store.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Datnuclear 30/07/2024 13:45
 * @project thd-store
 * @package com.thd.store.repository
 **/
public interface PersonRepository extends JpaRepository<Person,Long> {
    @Query(value = "select new com.thd.store.dto.customer.CustomerDto(entity) from Person entity " +
            "where (:keyword is null or :keyword = '' or entity.phoneNumber like concat('%',:keyword,'%') " +
            "or entity.displayName like concat('%',:keyword,'%') )")
    Page<Person> getPages(String keyword, Pageable pageable);
}
