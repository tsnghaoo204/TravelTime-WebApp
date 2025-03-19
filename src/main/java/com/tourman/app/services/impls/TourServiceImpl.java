package com.tourman.app.services.impls;

import com.tourman.app.domains.dtos.commons.TourDto;
import com.tourman.app.domains.dtos.mappers.TourMapping;
import com.tourman.app.domains.entities.Tour;
import com.tourman.app.domains.status.TourStatus;
import com.tourman.app.repositories.TourRepository;
import com.tourman.app.services.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapping tourMapping;

    @Override
    public List<TourDto> getTours() {
        return tourRepository.findAll()
                .stream()
                .map(tourMapping::toTourDto)
                .collect(Collectors.toList());
    }

    @Override
    public TourDto getTourById(Long id) {
        Tour findTour = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
        return tourMapping.toTourDto(findTour);
    }

    @Override
    public TourDto addTour(TourDto tour) {
        Tour newTour = tourRepository.save(tourMapping.toTour(tour));
        return tourMapping.toTourDto(newTour);
    }

//    @Override
//    public TourDto updateTour(Long id, TourDto tour) {
//        Tour foundTour = tourRepository.findById(id).orElseThrow(() -> new RuntimeException("Tour not found"));
//        foundTour.setTourName(tour.getTourName());
//        foundTour.setDescription(tour.getDescription());
//        foundTour.setPrice(tour.getPrice());
//        foundTour.setTourer(tour.getTourer());
//        foundTour.setStartDate(tour.getStartDate());
//        foundTour.setEndDate(tour.getEndDate());
//        foundTour.setMaxParticipants(tour.getMaxParticipants());
//        foundTour.setImageUrl(tour.getImageUrl());
//        foundTour.setStatus(TourStatus.valueOf(tour.getStatus().toUpperCase()));
//
//        Tour newTour = tourRepository.save(foundTour);
//        return tourMapping.toTourDto(newTour);
//    }

    @Override
    public String deleteTour(Long id) {
        tourRepository.deleteById(id);
        return "Successfully deleted tour";
    }
}
