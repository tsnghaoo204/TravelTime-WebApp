package com.tourman.app.controllers;

import com.tourman.app.domains.dtos.commons.TourDto;
import com.tourman.app.services.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllTours() {
        return ResponseEntity.ok(tourService.getTours());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getTourById(@RequestParam Long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourService.addTour(tourDto));
    }
}
