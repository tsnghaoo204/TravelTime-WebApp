package com.tourman.app.repositories;

import com.tourman.app.domains.entities.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
