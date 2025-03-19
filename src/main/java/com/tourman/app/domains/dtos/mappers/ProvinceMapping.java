package com.tourman.app.domains.dtos.mappers;

import com.tourman.app.domains.dtos.commons.ProvinceDto;
import com.tourman.app.domains.entities.Province;
import org.mapstruct.Mapper;

@Mapper(componentModel = "string")
public interface ProvinceMapping {
    ProvinceDto toDto(Province province);
    Province fromDto(ProvinceDto provinceDto);
}
