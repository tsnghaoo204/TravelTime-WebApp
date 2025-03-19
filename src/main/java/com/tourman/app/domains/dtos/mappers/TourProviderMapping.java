package com.tourman.app.domains.dtos.mappers;

import com.tourman.app.domains.dtos.commons.TourProviderDto;
import com.tourman.app.domains.entities.TourProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourProviderMapping {
    TourProviderDto tourProviderDto(TourProvider tourProvider);
    TourProvider tourProvider(TourProviderDto tourProviderDto);
}
