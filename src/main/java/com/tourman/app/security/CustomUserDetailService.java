package com.tourman.app.security;

import com.tourman.app.domains.entities.User;
import com.tourman.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
        User user =userRepository.findUserByEmailOrPhone(emailOrPhone,emailOrPhone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<GrantedAuthority> authorities = user.getRole()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
        String password = user.getPassword() == null? "OAUTH_USER" : user.getPassword();
        return new org.springframework.security.core.userdetails.User(emailOrPhone,password,authorities);
    }
}
