package com.thd.store.service.impl;

import com.thd.store.service.AuthService;
import com.thd.store.service.JwtService;
import com.thd.store.service.MailService;
import com.thd.store.dto.auth.AuthRequest;
import com.thd.store.dto.auth.AuthResponse;
import com.thd.store.dto.auth.ForgotPasswordRequest;
import com.thd.store.dto.auth.RegisterRequest;
import com.thd.store.dto.BaseResponse;
import com.thd.store.entity.*;
import com.thd.store.repository.*;
import com.thd.store.type.TokenType;
import com.thd.store.util.ConstUtil;
import com.thd.store.util.SystemMessage;
import com.thd.store.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author DatNuclear 16/01/2024
 * @project store-movie
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {
    @Value("${application.security.jwt.expiration.refreshToken}")
    private long refreshTokenExpiration;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public BaseResponse login(AuthRequest request) {
        HashMap<String, String> validations = validation(request);
        if (!CollectionUtils.isEmpty(validations)) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtService.generateToken(request.getUsername());
        var refreshToken = UUID.randomUUID().toString();
        revokeAllTokenAndRefreshToken(user.getUsername(),true);
        saveToken(user.getUsername(), token);
        saveRefreshToken(user.getUsername(), refreshToken);
        AuthResponse authResponse = AuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(token).build();
        return getResponse200(authResponse, getMessage(SystemMessage.SUCCESS));
    }

    @Override
    @Transactional
    public BaseResponse register(RegisterRequest request) {
        HashMap<String, String> validations = validation(request);
        boolean checkExistUsername = userRepository.existsByUsername(request.getUsername());
        if (checkExistUsername) {
            validations.put(SystemVariable.USERNAME, getMessage(SystemMessage.VALUE_EXIST, request.getUsername()));
        }
        boolean checkExistEmail = userRepository.existsByEmail(request.getEmail());
        if (checkExistEmail) {
            validations.put(SystemVariable.EMAIL, getMessage(SystemMessage.VALUE_EXIST, request.getEmail()));
        }
        if (!CollectionUtils.isEmpty(validations)) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        Role userRole = roleRepository.findByName(ConstUtil.USER_ROLE).orElse(Role.builder().name(ConstUtil.USER_ROLE).build());
        Person person = Person.builder()
                .email(request.getEmail())
                .build();
        User user = User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enabled(false)
                .accountNonExpired(false)
                .credentialsNonExpired(false)
                .accountNonLocked(false)
                .person(person)
                .roles(Set.of(userRole))
                .build();
        user = userRepository.save(user);
        mailService.sendMailActive(user);
        return getResponse200(true, getMessage(SystemMessage.REGISTER_SUCCESS));
    }

    @Override
    public BaseResponse verifyAccount(String token) {
        Optional<VerificationToken> tokenOptional = verificationTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            VerificationToken verificationToken = tokenOptional.get();
            if (LocalDateTime.now().isBefore(verificationToken.getExpiryTime())) {
                User user = verificationToken.getUser();
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                user = userRepository.save(user);
                return getResponse200(true, getMessage(SystemMessage.VERIFY_SUCCESS));
            }
            return getResponse400(getMessage(SystemMessage.TOKEN_EXPIRED));
        }
        return getResponse400(getMessage(SystemMessage.TOKEN_INVALID));
    }

    @Override
    public BaseResponse generateActiveToken(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            HashMap<String, String> validations = new HashMap<>();
            validations.put(SystemVariable.USERNAME, getMessage(SystemMessage.NOT_FOUND, username));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        User user = userOptional.get();
        if (user.isAccountNonLocked() && user.getAccountNonExpired()
                && user.getCredentialsNonExpired() && user.getEnabled()) {
            HashMap<String, String> validations = new HashMap<>();
            validations.put(SystemVariable.USERNAME, getMessage(SystemMessage.ACTIVATED, username));
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        mailService.sendMailActive(user);
        return getResponse200(true, getMessage(SystemMessage.GENERATE_TOKEN_SUCCESS));
    }

    @Override
    public BaseResponse forgotPassword(ForgotPasswordRequest request) {
        HashMap<String, String> validations = validation(request);
        if (!CollectionUtils.isEmpty(validations)) {
            return getResponse400(getMessage(SystemMessage.BAD_REQUEST), validations);
        }
        Optional<User> userOptional = userRepository.findByUsernameAndEmail(request.getUsername(), request.getEmail());
        if (userOptional.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.TWO_FIELD_NOT_MATCH, request.getUsername(), request.getEmail()));
        }
        User user = userOptional.get();
        VerificationToken verificationToken = verificationTokenRepository.findFirstByUserIdOrderByExpiryTimeDesc(user.getId());
        LocalDateTime now = LocalDateTime.now();
        if (verificationToken != null && verificationToken.getExpiryTime().isAfter(now)) {
            long diff = ChronoUnit.SECONDS.between(now, verificationToken.getExpiryTime());
            return getResponse400(getMessage(SystemMessage.ALREADY_SEND_FORGOT_PASS, request.getEmail(), diff));
        }
        mailService.sendMailForgotPassword(user);
        return getResponse200(true, getMessage(SystemMessage.CONTENT_SUCCESS_FORGOT_PASS, request.getEmail()));
    }

    @Override
    @Transactional
    public BaseResponse activeNewPassword(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.TOKEN_INVALID));
        }
        VerificationToken verificationToken = optionalToken.get();
        if (LocalDateTime.now().isAfter(verificationToken.getExpiryTime())) {
            return getResponse400(getMessage(SystemMessage.LINK_EXPIRED));
        }
        User user = verificationToken.getUser();
        user.setPassword(verificationToken.getPassword());
        userRepository.save(user);
        return getResponse200(true, getMessage(SystemMessage.ACTIVE_NEW_PASS_SUCCESS));
    }

    @Override
    @Transactional
    public BaseResponse generateNewToken(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return getResponse400(getMessage(SystemMessage.TOKEN_INVALID));
        }
        VerificationToken verificationToken = optionalToken.get();
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(verificationToken.getExpiryTime())) {
            long diff = ChronoUnit.SECONDS.between(now, verificationToken.getExpiryTime());
            return getResponse400(getMessage(SystemMessage.ALREADY_SEND_FORGOT_PASS, verificationToken.getUser().getEmail(), diff));
        }
        mailService.sendMailForgotPassword(verificationToken.getUser());
        return getResponse200(true, getMessage(SystemMessage.CONTENT_SUCCESS_FORGOT_PASS, verificationToken.getUser().getEmail()));
    }

    @Override
    public BaseResponse refreshToken(String token) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);
        if(refreshTokenOptional.isEmpty() || refreshTokenOptional.get().isRevoked()){
            return getResponse400(getMessage(SystemMessage.TOKEN_INVALID));
        }
        RefreshToken refreshToken = refreshTokenOptional.get();
        if(refreshToken.getExpiration().before(new Date())){
            return getResponse400(getMessage(SystemMessage.TOKEN_EXPIRED));
        }
        String jwtToken = jwtService.generateToken(refreshToken.getUsername());
//        String refreshTokenString = UUID.randomUUID().toString();
        revokeAllTokenAndRefreshToken(refreshToken.getUsername(),false);
        saveToken(refreshToken.getUsername(), jwtToken);
//        saveRefreshToken(refreshToken.getUsername(), refreshTokenString);
        AuthResponse authResponse = AuthResponse.builder()
                .refreshToken(token)
                .accessToken(jwtToken).build();
        return getResponse200(authResponse, getMessage(SystemMessage.SUCCESS));
    }

    private void saveToken(String username, String jwtToken) {
        var entity = tokenRepository.findByToken(jwtToken).orElse(null);
        if (entity != null) {
            return;
        }
        var token = Token.builder()
                .username(username)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveRefreshToken(String username, String jwtToken) {
        var token = RefreshToken.builder()
                .username(username)
                .token(jwtToken)
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .revoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    private void revokeAllTokenAndRefreshToken(String username, boolean revokeRefreshToken) {
        List<Token> tokens = tokenRepository.findAllByUsername(username);
        if (!CollectionUtils.isEmpty(tokens)) {
            tokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(tokens);
        }
        if(revokeRefreshToken){
            List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUsername(username);
            if (!CollectionUtils.isEmpty(refreshTokens)) {
                refreshTokens.forEach(token -> {
                    token.setRevoked(true);
                });
                refreshTokenRepository.saveAll(refreshTokens);
            }
        }
    }
}
