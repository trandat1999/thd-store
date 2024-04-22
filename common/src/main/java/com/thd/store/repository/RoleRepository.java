package com.thd.store.repository;

import com.thd.store.dto.user.RoleDto;
import com.thd.store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    @Query("select new com.thd.store.dto.user.RoleDto(entity) from Role entity " +
            "where (entity.voided is null or entity.voided = false)")
    List<RoleDto> getAll();
}
