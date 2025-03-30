package com.tourman.app.services;

import com.tourman.app.domains.dtos.responses.UserResponseDto;
import com.tourman.app.domains.entities.User;

public interface UserService {
    public UserResponseDto findById(long id);
    public UserResponseDto findByToken(String token);
    public void delete(long id);
}
