package com.tourman.app.domains.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
    private String roles;
}
