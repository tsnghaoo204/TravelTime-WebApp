package com.tourman.app.services;

import com.tourman.app.domains.dtos.commons.AttractionDto;

import java.util.List;

public interface AttractionService {
    List<AttractionDto> getAllAttractions();
    AttractionDto getAttractionById(Long id);
    AttractionDto createAttraction(AttractionDto attractionDto);
    AttractionDto updateAttraction(Long id ,AttractionDto attractionDto);
    void deleteAttraction(Long id);
}
