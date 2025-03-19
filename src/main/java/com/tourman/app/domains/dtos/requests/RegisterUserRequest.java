package com.tourman.app.domains.dtos.requests;

import com.tourman.app.domains.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
