package com.tourman.app.controllers;

import com.tourman.app.auths.impls.AuthServiceImpl;
import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RefreshTokenRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;
import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import com.tourman.app.repositories.RefreshTokenRepository;
import com.tourman.app.repositories.UserRepository;
import com.tourman.app.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        if (authServiceImpl.registerUser(registerUserRequest)) {
            return ResponseEntity.ok().body("{\"status\": \"success\", \"message\": \"Đăng ký thành công!\"}");
        }
        return ResponseEntity.badRequest().body("{\"status\": \"error\", \"message\": \"Đăng ký thất bại!\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest loginRequest) {
        JwtResponseDto response = authServiceImpl.loginUser(loginRequest);

        return ResponseEntity.ok(Map.of(
                "message", "Đăng nhập thành công!",
                "accessToken", response.getAccessToken(),
                "refreshToken", response.getRefreshToken(),
                "role", response.getRoles()
        ));
    }

    @PostMapping("/register-provider")
    public ResponseEntity<?> registerProvider(@RequestParam(value = "id") Long id ,@RequestBody TourProviderDto tourProvider){
        if (authServiceImpl.registerProvider(id, tourProvider)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refreshToken");

        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Refresh token không hợp lệ"));
        }

        // Tạo access token mới từ refresh token
        String newAccessToken = jwtTokenProvider.generateAccessTokenFromRefreshToken(refreshToken);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "message", "Token đã được làm mới!"
        ));
    }


    @GetMapping("/oauth2-success")
    public ResponseEntity<Map<String, Object>> loginSuccess(OAuth2User principal) {
        Map<String, Object> response = authServiceImpl.googleInfor(principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        refreshTokenRepository.findByToken(refreshToken).ifPresent(refreshTokenRepository::delete);

        return ResponseEntity.ok("Logged out successfully");
    }


}
