package com.thd.store.repository;

import com.thd.store.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);

    VerificationToken findFirstByUserIdOrderByExpiryTimeDesc(Long userId);
}
