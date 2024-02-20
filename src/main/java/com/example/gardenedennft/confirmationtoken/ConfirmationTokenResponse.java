package com.example.gardenedennft.confirmationtoken;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConfirmationTokenResponse extends JpaRepository<ConfirmationToken, UUID> {

    Optional<ConfirmationToken> findConfirmationTokenByToken(String token);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE ConfirmationToken c SET c.confirmAt = ?2 WHERE c.token = ?1 
           """)
    public void updateConfirmAtByToken(String token, LocalDateTime createdAt);

    Optional<List<ConfirmationToken>> findAllByConfirmAtAndExpiresAtBefore(LocalDateTime confirmAt, LocalDateTime expiresAt);
}
