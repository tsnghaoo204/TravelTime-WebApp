package com.tourman.app.repositories;

import com.tourman.app.domains.entities.TourProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourProviderRepository extends JpaRepository<TourProvider, Long> {
    boolean existsByEmail(String email);
}
