package com.tourman.app.auths.impls;

import com.tourman.app.auths.AuthService;
import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.mappers.TourProviderMapping;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;

import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import com.tourman.app.domains.entities.RefreshToken;
import com.tourman.app.domains.entities.Role;
import com.tourman.app.domains.entities.TourProvider;
import com.tourman.app.domains.entities.User;
import com.tourman.app.exceptions.EmailAlreadyExistsException;
import com.tourman.app.exceptions.PhoneAlreadyExistsException;
import com.tourman.app.repositories.RefreshTokenRepository;
import com.tourman.app.repositories.RoleRepository;
import com.tourman.app.repositories.TourProviderRepository;
import com.tourman.app.repositories.UserRepository;
import com.tourman.app.security.CustomUserDetailService;
import com.tourman.app.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TourProviderRepository tourProviderRepository;
    private final TourProviderMapping tourProviderMapping;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public boolean registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByPhone(request.getPhone())){
            throw new PhoneAlreadyExistsException();
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER");
        roles.add(role);
        user.setRole(roles);

        userRepository.save(user);
        return true;
    }

    @Override
    public JwtResponseDto loginUser(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailOrPhone()
                        , request.getPassword()
                )
        );

        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getEmailOrPhone());

        String accessToken = jwtTokenProvider.generateToken(new HashMap<>(), userDetails);
        String role =  jwtTokenProvider.getRoleFromToken(accessToken);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        return new JwtResponseDto(accessToken, role, refreshToken);
    }

    @Override
    public boolean registerProvider(Long userId ,TourProviderDto request) {
        if(tourProviderRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User us  = user.get();
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByRoleName("ROLE_PROVIDER");
            roles.add(role);
            us.setRole(roles);

        }
        TourProvider tourProvider = tourProviderMapping.tourProvider(request);
        tourProviderRepository.save(tourProvider);
        return true;
    }

    @Override
    public Map<String, Object> googleInfor(OAuth2User principal) {
        String email = principal.getAttribute("email");
        String firstName = (String) Optional.ofNullable(principal.getAttribute("given_name")).orElse("User");
        String lastName = (String) Optional.ofNullable(principal.getAttribute("family_name")).orElse("User");

        Optional<User> existingUser = userRepository.findUserByEmailOrPhone(email, email);
        User user = existingUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPhone(null);
            newUser.setPassword(null);
            Role role = roleRepository.findByRoleName("ROLE_USER");
            newUser.setRole(Set.of(role));
            return userRepository.save(newUser);
        });

        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            // có thể đặt một giá trị giả, vì password OAuth không dùng
            password = "{noop}oauthuser";
            // hoặc tạo chuỗi ngẫu nhiên, hoặc bất kỳ chuỗi nào không rỗng
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                password,
                user.getRole().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet())
        );

        String accessToken = jwtTokenProvider.generateToken(new HashMap<>(), userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        if (accessToken == null || refreshToken == null) {
            throw new IllegalStateException("Token generation failed");
        }

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }



    @Override
    public void logout(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }
}
