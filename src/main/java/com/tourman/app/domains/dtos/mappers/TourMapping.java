package com.tourman.app.domains.dtos.mappers;

import com.tourman.app.domains.dtos.commons.TourDto;
import com.tourman.app.domains.entities.Tour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapping {
    Tour toTour(TourDto tourDto);
    TourDto toTourDto(Tour tour);
}
