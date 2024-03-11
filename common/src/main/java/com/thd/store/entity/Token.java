package com.thd.store.entity;

import com.thd.store.type.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author DatNuclear 25/12/2023
 * @project store-movie
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_token")
public class Token extends BaseEntity{
    @Column(name="token",unique = true)
    private String token;
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private TokenType type = TokenType.BEARER;
    @Column(name="revoked")
    private boolean revoked;
    @Column(name="expired")
    private boolean expired;
    @Column(name="username")
    private String username;
}
