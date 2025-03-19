package com.tourman.app.repositories;

import com.tourman.app.domains.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
