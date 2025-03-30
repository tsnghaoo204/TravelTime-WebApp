package com.tourman.app.controllers;

import com.tourman.app.domains.dtos.responses.UserResponseDto;
import com.tourman.app.domains.entities.User;
import com.tourman.app.services.impls.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7).trim();
            } else {
                return ResponseEntity.status(400).body("{\"error\": \"Invalid token format\"}");
            }

            UserResponseDto user = userServiceImpl.findByToken(token);
            if (user == null) {
                return ResponseEntity.status(401).body("{\"error\": \"Unauthorized: Token invalid\"}");
            }

            // Đảm bảo trả về JSON thay vì chỉ trả về tên
            return ResponseEntity.ok(Map.of("firstName", user.getFirstName()));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Internal server error: " + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

}
