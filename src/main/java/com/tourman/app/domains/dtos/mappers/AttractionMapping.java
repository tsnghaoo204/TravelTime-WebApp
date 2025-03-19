package com.tourman.app.domains.dtos.mappers;

import com.tourman.app.domains.dtos.commons.AttractionDto;
import com.tourman.app.domains.entities.Attraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "string")
public interface AttractionMapping {
    AttractionDto toDto(Attraction attraction);
    Attraction fromDto(AttractionDto attractionDto);
}
