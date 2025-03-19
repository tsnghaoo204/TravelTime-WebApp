package com.tourman.app.repositories;

import com.tourman.app.domains.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<Tour> findByTourName(String tourName);
    boolean existsByTourName(String tourName);
}
