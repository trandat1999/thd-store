package com.thd.store.repository;

import com.thd.store.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author DatNuclear 25/12/2023
 * @project store-movie
 */
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
    @Query(value = "SELECT entity from Token entity " +
            "where entity.username =:username " +
            "and entity.expired = false " +
            "and entity.revoked = false ")
    List<Token> findAllByUsername(String username);
}
