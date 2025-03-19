package com.tourman.app.auths.impls;

import com.tourman.app.auths.AuthService;
import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.dtos.mappers.TourProviderMapping;
import com.tourman.app.domains.dtos.requests.LoginRequest;
import com.tourman.app.domains.dtos.requests.RegisterUserRequest;
import com.tourman.app.domains.dtos.responses.JwtResponseDto;
import com.tourman.app.domains.entities.Role;
import com.tourman.app.domains.entities.TourProvider;
import com.tourman.app.domains.entities.User;
import com.tourman.app.exceptions.EmailAlreadyExistsException;
import com.tourman.app.repositories.RoleRepository;
import com.tourman.app.repositories.TourProviderRepository;
import com.tourman.app.repositories.UserRepository;
import com.tourman.app.security.CustomUserDetailService;
import com.tourman.app.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

    @Override
    public boolean registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
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
        String token = jwtTokenProvider.generateToken(new HashMap<>(), userDetails);
        String roles = jwtTokenProvider.getRoleFromToken(token);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        return new JwtResponseDto(token,refreshToken,roles);
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


}
