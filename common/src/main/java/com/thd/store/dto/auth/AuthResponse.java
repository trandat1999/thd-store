package com.thd.store.dto.auth;

import lombok.*;

/**
 * @author DatNuclear 16/01/2024
 * @project store-movie
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
