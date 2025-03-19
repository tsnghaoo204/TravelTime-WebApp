package com.tourman.app.auths;

import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;
import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import com.tourman.app.domains.entities.TourProvider;

public interface AuthService {
    public boolean registerUser(RegisterUserRequest request);
    public JwtResponseDto loginUser(LoginRequest request);
    public boolean registerProvider(Long userId,TourProviderDto request);
}
