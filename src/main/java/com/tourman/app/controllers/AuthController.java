package com.tourman.app.controllers;

import com.tourman.app.auths.impls.AuthServiceImpl;
import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;
import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        if (authServiceImpl.registerUser(registerUserRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authServiceImpl.loginUser(loginRequest));
    }
    @PostMapping("/register-provider")
    public ResponseEntity<?> registerProvider(@RequestParam(value = "id") Long id ,@RequestBody TourProviderDto tourProvider){
        if (authServiceImpl.registerProvider(id, tourProvider)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
