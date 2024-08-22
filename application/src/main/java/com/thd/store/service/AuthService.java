package com.thd.store.service;

import com.thd.store.dto.auth.AuthRequest;
import com.thd.store.dto.auth.ForgotPasswordRequest;
import com.thd.store.dto.auth.OAuthRequest;
import com.thd.store.dto.auth.RegisterRequest;
import com.thd.store.dto.BaseResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author DatNuclear 16/01/2024
 * @project store-movie
 */
public interface AuthService {
    BaseResponse login(AuthRequest request);
    BaseResponse register(RegisterRequest request);
    BaseResponse verifyAccount(String token);
    BaseResponse generateActiveToken(String username);
    BaseResponse forgotPassword(ForgotPasswordRequest request);
    BaseResponse activeNewPassword(String token);
    BaseResponse generateNewToken(String token);
    BaseResponse refreshToken(String token);
    BaseResponse loginGoogle(OAuthRequest request) throws GeneralSecurityException, IOException;
}
