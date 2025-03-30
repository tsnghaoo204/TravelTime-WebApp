package com.tourman.app.configs;

import com.tourman.app.auths.impls.AuthServiceImpl;
import com.tourman.app.security.CustomUserDetailService;
import com.tourman.app.security.JwtAuthEntryPoint;
import com.tourman.app.security.JwtAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, AuthServiceImpl authServiceImpl) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);  // ✅ Bắt buộc để cho phép cookie
                    config.setExposedHeaders(List.of("Set-Cookie"));  // ✅ Cho phép frontend đọc `Set-Cookie`
                    return config;
                }))

                .authorizeHttpRequests((auth) -> {
//                        auth.requestMatchers("/api/auth/**").permitAll();
//                        auth.requestMatchers(HttpMethod.GET, "/api/v1/user").permitAll();
                        auth.anyRequest().permitAll();
                    }
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            OAuth2User principal = (OAuth2User) authentication.getPrincipal();
                            Map<String, Object> authResponse = authServiceImpl.googleInfor(principal);

                            if (authResponse == null || !authResponse.containsKey("accessToken") || !authResponse.containsKey("refreshToken")) {
                                response.sendRedirect("http://localhost:3000/oauth-callback?error=invalid_token");
                                return;
                            }

                            String accessToken = (String) authResponse.get("accessToken");
                            String refreshToken = (String) authResponse.get("refreshToken");

                            // Redirect về frontend và gửi token qua query params
                            response.sendRedirect("http://localhost:3000/oauth-callback?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
                        })


                )

                .httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
