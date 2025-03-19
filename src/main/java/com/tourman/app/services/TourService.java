package com.tourman.app.services;

import com.tourman.app.domains.dtos.commons.TourDto;
import com.tourman.app.domains.entities.Tour;

import java.util.List;

public interface TourService {
    List<TourDto> getTours();
    TourDto getTourById(Long id);
    TourDto addTour(TourDto tour);
    //TourDto updateTour(Long id ,TourDto tour);
    String deleteTour(Long id);
}
