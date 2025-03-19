package com.tourman.app.repositories;

import com.tourman.app.domains.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleUser);
}
