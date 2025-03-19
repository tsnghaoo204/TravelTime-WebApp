package com.tourman.app.repositories;

import com.tourman.app.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String firstName);
    Optional<User> findUserByEmailOrPhone(String email, String phone);
    boolean existsByEmail(String email);
}
