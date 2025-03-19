package com.tourman.app.repositories;

import com.tourman.app.domains.entities.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
