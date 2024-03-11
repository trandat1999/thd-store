package com.thd.store.repository;

import com.thd.store.dto.country.CountryDto;
import com.thd.store.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
public interface CountryRepository extends JpaRepository<Country,Long> {
    @Query(value = "select count(1) from Country entity where entity.code = :code " +
            "and (:id is null or entity.id != :id)")
    long countExistByCode(String code, Long id);

    @Query(value = "select new com.thd.store.dto.country.CountryDto(entity) " +
            "from Country entity where entity.voided <> true")
    List<CountryDto> getAll();

    @Query(value = "select new com.thd.store.dto.country.CountryDto(entity) from Country entity " +
            "where (:voided is null or entity.voided =:voided) " +
            "and (:keyword is null or :keyword = '' or entity.code like concat('%',:keyword,'%') " +
            "or entity.name like concat('%',:keyword,'%') )")
    Page<CountryDto> search(String keyword, Boolean voided, Pageable pageable);
}
