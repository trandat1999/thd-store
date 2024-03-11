package com.thd.store.repository;

import com.thd.store.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author DatNuclear 18/01/2024
 * @project store-movie
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    @Query(value = "SELECT entity from RefreshToken entity " +
            "where entity.username =:username " +
            "and entity.revoked = false")
    List<RefreshToken> findAllByUsername(String username);
}
