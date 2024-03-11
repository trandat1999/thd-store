package com.thd.store.config;

import com.thd.store.repository.RefreshTokenRepository;
import com.thd.store.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import static com.thd.store.util.ConstUtil.AUTHORIZATION;
import static com.thd.store.util.ConstUtil.BEARER;


/**
 * @author DatNuclear 23/01/2024
 * @project store-movie
 */
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null ||!authHeader.startsWith(BEARER)) {
            return;
        }
        final String jwt = authHeader.substring(BEARER.length());

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            var refreshTokens = refreshTokenRepository.findAllByUsername(storedToken.getUsername());
            if(!CollectionUtils.isEmpty(refreshTokens)){
                refreshTokens.forEach(refreshToken -> refreshToken.setRevoked(true));
                refreshTokenRepository.saveAll(refreshTokens);
            }
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
