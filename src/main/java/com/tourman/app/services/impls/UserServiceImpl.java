package com.tourman.app.services.impls;

import com.tourman.app.domains.dtos.responses.UserResponseDto;
import com.tourman.app.domains.entities.User;
import com.tourman.app.repositories.UserRepository;
import com.tourman.app.security.JwtTokenProvider;
import com.tourman.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponseDto findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return userMapping(user);
    }


    @Override
    public UserResponseDto findByToken(String token) {
        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userRepository.findUserByEmailOrPhone(username, username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return userMapping(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto userMapping(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        return userResponseDto;
    }
}
