package com.tourman.app.auths;

import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;

import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface AuthService {
    public boolean registerUser(RegisterUserRequest request);
    public JwtResponseDto loginUser(LoginRequest request);
    public boolean registerProvider(Long userId,TourProviderDto request);
    public Map<String, Object> googleInfor(OAuth2User principle);
    public void logout(String username);
}
