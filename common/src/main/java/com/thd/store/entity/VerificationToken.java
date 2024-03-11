package com.thd.store.entity;

import com.thd.store.type.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
@Entity
@Table(name = "tbl_verification_token")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken extends BaseEntity{
    @Column(name = "token")
    private String token;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenType type;
    @Column(name = "password")
    private String password;
}
